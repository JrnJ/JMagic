package com.jeroenj.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ManaBoltEntity extends JEntity {
    public static final int DESPAWN_AFTER = 10 * 20; // Seconds * 20

    private Vec3d velocity;

    public ManaBoltEntity(EntityType<?> type, World world) {
        super(type, world, DESPAWN_AFTER);
    }

    public void start(Vec3d spawnPosition, Vec3d velocity, float yaw, float pitch) {
        this.setPosition(spawnPosition);
        this.velocity = velocity;
        this.setRotation(yaw, pitch);
        this.setVelocity(this.velocity);
    }

    @Override
    public void tick() {
        super.tick();
        velocity = getDirectionVector();
        this.setVelocity(velocity);
        this.move(MovementType.SELF, this.getVelocity());
    }

    @Override
    protected void beforeDespawn() {
        super.beforeDespawn();
    }

    //
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
