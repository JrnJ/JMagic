package com.jeroenj.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class JEntity extends Entity {

    protected int despawnTimer;

    public JEntity(EntityType<?> type, World world, int despawnAfter) {
        super(type, world);
        this.despawnTimer = despawnAfter;
    }

    @Override
    public void tick() {
        super.tick();
        if (despawnTimer <= 0) {
            beforeDespawn();
            this.remove(RemovalReason.DISCARDED);
        } else {
            despawnTimer--;
        }
    }

    protected void beforeDespawn() {

    }

    protected Vec3d getDirectionVector() {
        double yawRad = Math.toRadians(-getYaw());
        double pitchRad = Math.toRadians(-getPitch());

        double x = Math.sin(yawRad) * Math.cos(pitchRad);
        double y = Math.sin(pitchRad);
        double z = Math.cos(yawRad) * Math.cos(pitchRad);

        return new Vec3d(x, y, z);
    }
}
