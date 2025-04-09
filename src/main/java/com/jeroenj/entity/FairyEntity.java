package com.jeroenj.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.TargetUtil;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.GameEventTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class FairyEntity extends PathAwareEntity implements InventoryOwner, Vibrations {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Vec3i ITEM_PICKUP_RANGE_EXPANDER = new Vec3i(1, 1, 1);
    private static final int field_39461 = 5;
    private static final float field_39462 = 55.0F;
    private static final float field_39463 = 15.0F;
    private static final int DUPLICATION_COOLDOWN = 6000;
    private static final int field_39679 = 3;
    public static final int field_54974 = 1024;
    private static final TrackedData<Boolean> DANCING;
    private static final TrackedData<Boolean> CAN_DUPLICATE;
    protected static final ImmutableList<SensorType<? extends Sensor<? super FairyEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;
    public static final ImmutableList<Float> THROW_SOUND_PITCHES;
    private final EntityGameEventHandler<Vibrations.VibrationListener> gameEventHandler;
    private Vibrations.ListenerData vibrationListenerData;
    private final Vibrations.Callback vibrationCallback;
    private final EntityGameEventHandler<JukeboxEventListener> jukeboxEventHandler;
    private final SimpleInventory inventory = new SimpleInventory(1);
    @Nullable
    private BlockPos jukeboxPos;
    private long duplicationCooldown;
    private float itemHoldAnimationTicks;
    private float prevItemHoldAnimationTicks;
    private float danceTicks;
    private float spinningAnimationTicks;
    private float prevSpinningAnimationTicks;

    public FairyEntity(EntityType<? extends FairyEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setCanPickUpLoot(this.canPickUpLoot());
        this.vibrationCallback = new VibrationCallback();
        this.vibrationListenerData = new Vibrations.ListenerData();
        this.gameEventHandler = new EntityGameEventHandler(new Vibrations.VibrationListener(this));
        this.jukeboxEventHandler = new EntityGameEventHandler(new JukeboxEventListener(this.vibrationCallback.getPositionSource(), ((GameEvent)GameEvent.JUKEBOX_PLAY.value()).notificationRadius()));
    }

    protected Brain.Profile<FairyEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return FairyBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    public Brain<FairyEntity> getBrain() {
        return (Brain<FairyEntity>) super.getBrain();
    }

    public static DefaultAttributeContainer.Builder createFairyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.MAX_HEALTH, 20.0).add(EntityAttributes.FLYING_SPEED, 0.10000000149011612).add(EntityAttributes.MOVEMENT_SPEED, 0.10000000149011612).add(EntityAttributes.ATTACK_DAMAGE, 2.0);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setMaxFollowRange(48.0F);
        return birdNavigation;
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(DANCING, false);
        builder.add(CAN_DUPLICATE, true);
    }

    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement()) {
            if (this.isTouchingWater()) {
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.800000011920929));
            } else if (this.isInLava()) {
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.5));
            } else {
                this.updateVelocity(this.getMovementSpeed(), movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.9100000262260437));
            }
        }

    }

    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return this.isLikedBy(source.getAttacker()) ? false : super.damage(world, source, amount);
    }

    protected boolean isInSameTeam(Entity other) {
        return this.isLikedBy(other) || super.isInSameTeam(other);
    }

    private boolean isLikedBy(@Nullable Entity player) {
        if (!(player instanceof PlayerEntity playerEntity)) {
            return false;
        } else {
            Optional<UUID> optional = this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LIKED_PLAYER);
            return optional.isPresent() && playerEntity.getUuid().equals(optional.get());
        }
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    protected SoundEvent getAmbientSound() {
        return this.hasStackEquipped(EquipmentSlot.MAINHAND) ? SoundEvents.ENTITY_ALLAY_AMBIENT_WITH_ITEM : SoundEvents.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ALLAY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ALLAY_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("fairyBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("fairyActivityUpdate");
        FairyBrain.updateActivities(this);
        profiler.pop();
        super.mobTick(world);
    }

    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient && this.isAlive() && this.age % 10 == 0) {
            this.heal(1.0F);
        }

        if (this.isDancing() && this.shouldStopDancing() && this.age % 20 == 0) {
            this.setDancing(false);
            this.jukeboxPos = null;
        }

        this.tickDuplicationCooldown();
    }

    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            this.prevItemHoldAnimationTicks = this.itemHoldAnimationTicks;
            if (this.isHoldingItem()) {
                this.itemHoldAnimationTicks = MathHelper.clamp(this.itemHoldAnimationTicks + 1.0F, 0.0F, 5.0F);
            } else {
                this.itemHoldAnimationTicks = MathHelper.clamp(this.itemHoldAnimationTicks - 1.0F, 0.0F, 5.0F);
            }

            if (this.isDancing()) {
                ++this.danceTicks;
                this.prevSpinningAnimationTicks = this.spinningAnimationTicks;
                if (this.isSpinning()) {
                    ++this.spinningAnimationTicks;
                } else {
                    --this.spinningAnimationTicks;
                }

                this.spinningAnimationTicks = MathHelper.clamp(this.spinningAnimationTicks, 0.0F, 15.0F);
            } else {
                this.danceTicks = 0.0F;
                this.spinningAnimationTicks = 0.0F;
                this.prevSpinningAnimationTicks = 0.0F;
            }
        } else {
            Ticker.tick(this.getWorld(), this.vibrationListenerData, this.vibrationCallback);
            if (this.isPanicking()) {
                this.setDancing(false);
            }
        }

    }

    public boolean canPickUpLoot() {
        return !this.isItemPickupCoolingDown() && this.isHoldingItem();
    }

    public boolean isHoldingItem() {
        return !this.getStackInHand(Hand.MAIN_HAND).isEmpty();
    }

    protected boolean canDispenserEquipSlot(EquipmentSlot slot) {
        return false;
    }

    private boolean isItemPickupCoolingDown() {
        return this.getBrain().isMemoryInState(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryModuleState.VALUE_PRESENT);
    }

    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ItemStack itemStack2 = this.getStackInHand(Hand.MAIN_HAND);
        if (this.isDancing() && itemStack.isIn(ItemTags.DUPLICATES_ALLAYS) && this.canDuplicate()) {
            this.duplicate();
            this.getWorld().sendEntityStatus(this, (byte)18);
            this.getWorld().playSoundFromEntity(player, this, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.NEUTRAL, 2.0F, 1.0F);
            this.decrementStackUnlessInCreative(player, itemStack);
            return ActionResult.SUCCESS;
        } else if (itemStack2.isEmpty() && !itemStack.isEmpty()) {
            ItemStack itemStack3 = itemStack.copyWithCount(1);
            this.setStackInHand(Hand.MAIN_HAND, itemStack3);
            this.decrementStackUnlessInCreative(player, itemStack);
            this.getWorld().playSoundFromEntity(player, this, SoundEvents.ENTITY_ALLAY_ITEM_GIVEN, SoundCategory.NEUTRAL, 2.0F, 1.0F);
            this.getBrain().remember(MemoryModuleType.LIKED_PLAYER, player.getUuid());
            return ActionResult.SUCCESS;
        } else if (!itemStack2.isEmpty() && hand == Hand.MAIN_HAND && itemStack.isEmpty()) {
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            this.getWorld().playSoundFromEntity(player, this, SoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.NEUTRAL, 2.0F, 1.0F);
            this.swingHand(Hand.MAIN_HAND);
            Iterator var5 = this.getInventory().clearToList().iterator();

            while(var5.hasNext()) {
                ItemStack itemStack4 = (ItemStack)var5.next();
                TargetUtil.give(this, itemStack4, this.getPos());
            }

            this.getBrain().forget(MemoryModuleType.LIKED_PLAYER);
            player.giveItemStack(itemStack2);
            return ActionResult.SUCCESS;
        } else {
            return super.interactMob(player, hand);
        }
    }

    public void updateJukeboxPos(BlockPos jukeboxPos, boolean playing) {
        if (playing) {
            if (!this.isDancing()) {
                this.jukeboxPos = jukeboxPos;
                this.setDancing(true);
            }
        } else if (jukeboxPos.equals(this.jukeboxPos) || this.jukeboxPos == null) {
            this.jukeboxPos = null;
            this.setDancing(false);
        }

    }

    public SimpleInventory getInventory() {
        return this.inventory;
    }

    protected Vec3i getItemPickUpRangeExpander() {
        return ITEM_PICKUP_RANGE_EXPANDER;
    }

    public boolean canGather(ServerWorld world, ItemStack stack) {
        ItemStack itemStack = this.getStackInHand(Hand.MAIN_HAND);
        return !itemStack.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && this.inventory.canInsert(stack) && this.areItemsEqual(itemStack, stack);
    }

    private boolean areItemsEqual(ItemStack stack, ItemStack stack2) {
        return ItemStack.areItemsEqual(stack, stack2) && !this.areDifferentPotions(stack, stack2);
    }

    private boolean areDifferentPotions(ItemStack stack, ItemStack stack2) {
        PotionContentsComponent potionContentsComponent = (PotionContentsComponent)stack.get(DataComponentTypes.POTION_CONTENTS);
        PotionContentsComponent potionContentsComponent2 = (PotionContentsComponent)stack2.get(DataComponentTypes.POTION_CONTENTS);
        return !Objects.equals(potionContentsComponent, potionContentsComponent2);
    }

    protected void loot(ServerWorld world, ItemEntity itemEntity) {
        InventoryOwner.pickUpItem(world, this, this, itemEntity);
    }

    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    public boolean isFlappingWings() {
        return !this.isOnGround();
    }

    public void updateEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback) {
        World var3 = this.getWorld();
        if (var3 instanceof ServerWorld serverWorld) {
            callback.accept(this.gameEventHandler, serverWorld);
            callback.accept(this.jukeboxEventHandler, serverWorld);
        }

    }

    public boolean isDancing() {
        return (Boolean)this.dataTracker.get(DANCING);
    }

    public void setDancing(boolean dancing) {
        if (!this.getWorld().isClient && this.canMoveVoluntarily() && (!dancing || !this.isPanicking())) {
            this.dataTracker.set(DANCING, dancing);
        }
    }

    private boolean shouldStopDancing() {
        return this.jukeboxPos == null || !this.jukeboxPos.isWithinDistance(this.getPos(), (double)((GameEvent)GameEvent.JUKEBOX_PLAY.value()).notificationRadius()) || !this.getWorld().getBlockState(this.jukeboxPos).isOf(Blocks.JUKEBOX);
    }

    public float getItemHoldAnimationTicks(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.prevItemHoldAnimationTicks, this.itemHoldAnimationTicks) / 5.0F;
    }

    public boolean isSpinning() {
        float f = this.danceTicks % 55.0F;
        return f < 15.0F;
    }

    public float getSpinningAnimationTicks(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.prevSpinningAnimationTicks, this.spinningAnimationTicks) / 15.0F;
    }

    public boolean areItemsDifferent(ItemStack stack, ItemStack stack2) {
        return !this.areItemsEqual(stack, stack2);
    }

    protected void dropInventory(ServerWorld world) {
        super.dropInventory(world);
        this.inventory.clearToList().forEach((stack) -> {
            this.dropStack(world, stack);
        });
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty() && !EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, EnchantmentEffectComponentTypes.PREVENT_EQUIPMENT_DROP)) {
            this.dropStack(world, itemStack);
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeInventory(nbt, this.getRegistryManager());
        RegistryOps<NbtElement> registryOps = this.getRegistryManager().getOps(NbtOps.INSTANCE);
        ListenerData.CODEC.encodeStart(registryOps, this.vibrationListenerData).resultOrPartial((string) -> {
            LOGGER.error("Failed to encode vibration listener for Fairy: '{}'", string);
        }).ifPresent((encodedVibrationListenerData) -> {
            nbt.put("listener", encodedVibrationListenerData);
        });
        nbt.putLong("DuplicationCooldown", this.duplicationCooldown);
        nbt.putBoolean("CanDuplicate", this.canDuplicate());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readInventory(nbt, this.getRegistryManager());
        RegistryOps<NbtElement> registryOps = this.getRegistryManager().getOps(NbtOps.INSTANCE);
        if (nbt.contains("listener", 10)) {
            ListenerData.CODEC.parse(registryOps, nbt.getCompound("listener")).resultOrPartial((string) -> {
                LOGGER.error("Failed to parse vibration listener for Fairy: '{}'", string);
            }).ifPresent((vibrationListenerData) -> {
                this.vibrationListenerData = vibrationListenerData;
            });
        }

        this.duplicationCooldown = (long)nbt.getInt("DuplicationCooldown");
        this.dataTracker.set(CAN_DUPLICATE, nbt.getBoolean("CanDuplicate"));
    }

    protected boolean shouldFollowLeash() {
        return false;
    }

    private void tickDuplicationCooldown() {
        if (this.duplicationCooldown > 0L) {
            --this.duplicationCooldown;
        }

        if (!this.getWorld().isClient() && this.duplicationCooldown == 0L && !this.canDuplicate()) {
            this.dataTracker.set(CAN_DUPLICATE, true);
        }

    }

    private void duplicate() {
        FairyEntity fairyEntity = (FairyEntity)JMagicEntities.FAIRY.create(this.getWorld(), SpawnReason.BREEDING);
        if (fairyEntity != null) {
            fairyEntity.refreshPositionAfterTeleport(this.getPos());
            fairyEntity.setPersistent();
            fairyEntity.startDuplicationCooldown();
            this.startDuplicationCooldown();
            this.getWorld().spawnEntity(fairyEntity);
        }

    }

    private void startDuplicationCooldown() {
        this.duplicationCooldown = 6000L;
        this.dataTracker.set(CAN_DUPLICATE, false);
    }

    private boolean canDuplicate() {
        return (Boolean)this.dataTracker.get(CAN_DUPLICATE);
    }

    private void decrementStackUnlessInCreative(PlayerEntity player, ItemStack stack) {
        stack.decrementUnlessCreative(1, player);
    }

    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, (double)this.getStandingEyeHeight() * 0.6, (double)this.getWidth() * 0.1);
    }

    public void handleStatus(byte status) {
        if (status == 18) {
            for(int i = 0; i < 3; ++i) {
                this.addHeartParticle();
            }
        } else {
            super.handleStatus(status);
        }

    }

    private void addHeartParticle() {
        double d = this.random.nextGaussian() * 0.02;
        double e = this.random.nextGaussian() * 0.02;
        double f = this.random.nextGaussian() * 0.02;
        this.getWorld().addParticle(ParticleTypes.HEART, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
    }

    public Vibrations.ListenerData getVibrationListenerData() {
        return this.vibrationListenerData;
    }

    public Vibrations.Callback getVibrationCallback() {
        return this.vibrationCallback;
    }

    static {
        DANCING = DataTracker.registerData(FairyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        CAN_DUPLICATE = DataTracker.registerData(FairyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, SensorType.NEAREST_ITEMS);
        MEMORY_MODULES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.HURT_BY, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.LIKED_PLAYER, MemoryModuleType.LIKED_NOTEBLOCK, MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryModuleType.IS_PANICKING, new MemoryModuleType[0]);
        THROW_SOUND_PITCHES = ImmutableList.of(0.5625F, 0.625F, 0.75F, 0.9375F, 1.0F, 1.0F, 1.125F, 1.25F, 1.5F, 1.875F, 2.0F, 2.25F, new Float[]{2.5F, 3.0F, 3.75F, 4.0F});
    }

    private class VibrationCallback implements Vibrations.Callback {
        private static final int RANGE = 16;
        private final PositionSource positionSource = new EntityPositionSource(FairyEntity.this, FairyEntity.this.getStandingEyeHeight());

        VibrationCallback() {
        }

        public int getRange() {
            return 16;
        }

        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        public boolean accepts(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter) {
            if (FairyEntity.this.isAiDisabled()) {
                return false;
            } else {
                Optional<GlobalPos> optional = FairyEntity.this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LIKED_NOTEBLOCK);
                if (optional.isEmpty()) {
                    return true;
                } else {
                    GlobalPos globalPos = (GlobalPos)optional.get();
                    return globalPos.isWithinRange(world.getRegistryKey(), FairyEntity.this.getBlockPos(), 1024) && globalPos.pos().equals(pos);
                }
            }
        }

        public void accept(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, @Nullable Entity sourceEntity, @Nullable Entity entity, float distance) {
            if (event.matches(GameEvent.NOTE_BLOCK_PLAY)) {
                FairyBrain.rememberNoteBlock(FairyEntity.this, new BlockPos(pos));
            }

        }

        public TagKey<GameEvent> getTag() {
            return GameEventTags.ALLAY_CAN_LISTEN;
        }
    }

    private class JukeboxEventListener implements GameEventListener {
        private final PositionSource positionSource;
        private final int range;

        public JukeboxEventListener(final PositionSource positionSource, final int range) {
            this.positionSource = positionSource;
            this.range = range;
        }

        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        public int getRange() {
            return this.range;
        }

        public boolean listen(ServerWorld world, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter, Vec3d emitterPos) {
            if (event.matches(GameEvent.JUKEBOX_PLAY)) {
                FairyEntity.this.updateJukeboxPos(BlockPos.ofFloored(emitterPos), true);
                return true;
            } else if (event.matches(GameEvent.JUKEBOX_STOP_PLAY)) {
                FairyEntity.this.updateJukeboxPos(BlockPos.ofFloored(emitterPos), false);
                return true;
            } else {
                return false;
            }
        }
    }
}
