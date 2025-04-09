package com.jeroenj.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class FairyBrain {
    private static final float field_38406 = 1.0F;
    private static final float field_38407 = 2.25F;
    private static final float WALK_TO_ITEM_SPEED = 1.75F;
    private static final float FLEE_SPEED = 2.5F;
    private static final int field_38938 = 4;
    private static final int field_38939 = 16;
    private static final int field_38410 = 6;
    private static final int field_38411 = 30;
    private static final int field_38412 = 60;
    private static final int LIKED_NOTEBLOCK_COOLDOWN_TICKS_EXPIRY = 600;
    private static final int WALK_TO_ITEM_RADIUS = 32;
    private static final int GIVE_INVENTORY_RUN_TIME = 20;

    public FairyBrain() {

    }

    protected static Brain<?> create(Brain<FairyEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<FairyEntity> brain) {
        brain.setTaskList(
                Activity.CORE, 0,
                (ImmutableList<? extends Task<? super FairyEntity>>) ImmutableList.of(new StayAboveWaterTask(0.8F),
                        new FleeTask(2.5F),
                        new UpdateLookControlTask(45, 90),
                        new MoveToTargetTask(),
                        new TickCooldownTask(MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS),
                        new TickCooldownTask(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS))
        );
    }

    private static void addIdleActivities(Brain<FairyEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, WalkTowardsNearestVisibleWantedItemTask.create((fairy) -> {
            return true;
        }, 1.75F, true, 32)), Pair.of(1, new GiveInventoryToLookTargetTask(FairyBrain::getLookTarget, 2.25F, 20)), Pair.of(2, WalkTowardsLookTargetTask.create(FairyBrain::getLookTarget, Predicate.not(FairyBrain::hasNearestVisibleWantedItem), 4, 16, 2.25F)), Pair.of(3, LookAtMobWithIntervalTask.follow(6.0F, UniformIntProvider.create(30, 60))), Pair.of(4, new RandomTask(ImmutableList.of(Pair.of(StrollTask.createSolidTargeting(1.0F), 2), Pair.of(GoToLookTargetTask.create(1.0F, 3), 2), Pair.of(new WaitTask(30, 60), 1))))), ImmutableSet.of());
    }

    public static void updateActivities(FairyEntity fairy) {
        fairy.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    public static void rememberNoteBlock(LivingEntity fairy, BlockPos pos) {
        Brain<?> brain = fairy.getBrain();
        GlobalPos globalPos = GlobalPos.create(fairy.getWorld().getRegistryKey(), pos);
        Optional<GlobalPos> optional = brain.getOptionalRegisteredMemory(MemoryModuleType.LIKED_NOTEBLOCK);
        if (optional.isEmpty()) {
            brain.remember(MemoryModuleType.LIKED_NOTEBLOCK, globalPos);
            brain.remember(MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS, 600);
        } else if (((GlobalPos)optional.get()).equals(globalPos)) {
            brain.remember(MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS, 600);
        }

    }

    private static Optional<LookTarget> getLookTarget(LivingEntity fairy) {
        Brain<?> brain = fairy.getBrain();
        Optional<GlobalPos> optional = brain.getOptionalRegisteredMemory(MemoryModuleType.LIKED_NOTEBLOCK);
        if (optional.isPresent()) {
            GlobalPos globalPos = (GlobalPos)optional.get();
            if (shouldGoTowardsNoteBlock(fairy, brain, globalPos)) {
                return Optional.of(new BlockPosLookTarget(globalPos.pos().up()));
            }

            brain.forget(MemoryModuleType.LIKED_NOTEBLOCK);
        }

        return getLikedLookTarget(fairy);
    }

    private static boolean hasNearestVisibleWantedItem(LivingEntity entity) {
        Brain<?> brain = entity.getBrain();
        return brain.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
    }

    private static boolean shouldGoTowardsNoteBlock(LivingEntity fairy, Brain<?> brain, GlobalPos pos) {
        Optional<Integer> optional = brain.getOptionalRegisteredMemory(MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS);
        World world = fairy.getWorld();
        return pos.isWithinRange(world.getRegistryKey(), fairy.getBlockPos(), 1024) && world.getBlockState(pos.pos()).isOf(Blocks.NOTE_BLOCK) && optional.isPresent();
    }

    private static Optional<LookTarget> getLikedLookTarget(LivingEntity fairy) {
        return getLikedPlayer(fairy).map((player) -> {
            return new EntityLookTarget(player, true);
        });
    }

    public static Optional<ServerPlayerEntity> getLikedPlayer(LivingEntity fairy) {
        World world = fairy.getWorld();
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            Optional<UUID> optional = fairy.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LIKED_PLAYER);
            if (optional.isPresent()) {
                Entity entity = serverWorld.getEntity((UUID)optional.get());
                if (entity instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
                    if ((serverPlayerEntity.interactionManager.isSurvivalLike() || serverPlayerEntity.interactionManager.isCreative()) && serverPlayerEntity.isInRange(fairy, 64.0)) {
                        return Optional.of(serverPlayerEntity);
                    }
                }

                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}
