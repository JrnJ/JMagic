package com.jeroenj.entity;

import com.jeroenj.util.JHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.List;

public class MeteorEntity extends Entity {
    public static final double FALL_SPEED = -0.1;
    public static final int DESPAWN_AFTER = 15 * 20; // Seconds * 20
    private int lifetime = 0;

    public MeteorEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        setVelocity(0.0, FALL_SPEED - 0.4, 0.0);
        move(MovementType.SELF, getVelocity());
        World world = getWorld();
        if (!world.isClient()) {
            JHelper.spawnServerParticle(world, ParticleTypes.FLAME, getPos(), 3, 0.0, 0.0, 0.0, 0.0);

            if (BlockPos.stream(getBoundingBox().expand(0.75))
                    .map(world::getBlockState)
                    .filter(state -> !state.isOf(Blocks.AIR))
                    .toArray().length > 0)
            {
                onHit();
            }
//            if (world.getBlockState(getBlockPos().down()).isOpaque()) {
//                onHit();
//            }
        }

        if (lifetime >= DESPAWN_AFTER) {
            this.remove(RemovalReason.DISCARDED);
        }
        lifetime++;
    }

    private void onHit() {
        JHelper.playServerSound(this, SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
        JHelper.spawnServerParticle(getWorld(), ParticleTypes.EXPLOSION, getPos(), 3, 0.0, 0.0, 0.0, 0.0);

        getWorld().createExplosion(this, Explosion.createDamageSource(this.getWorld(), this), null, getX(), getY(), getZ(), 10.0f, false, World.ExplosionSourceType.NONE );

//        double radius = 2.5;
//        Box area = new Box(getPos().add(-radius, -radius, -radius), getPos().add(radius, radius, radius));
//        List<Entity> entitiesInArea = getWorld().getEntitiesByClass(Entity.class, area, Entity::isAttackable);
//
//        for (Entity entity : entitiesInArea) {
//            if (entity instanceof LivingEntity livingEntity) {
//                livingEntity.damage((ServerWorld) getWorld(), )
//            }
//        }

        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
