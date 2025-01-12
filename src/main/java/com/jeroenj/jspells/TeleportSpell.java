package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class TeleportSpell extends JSpell {
    public static final float MAX_DISTANCE = 20.0f;
    public static final SoundEvent CAST_SOUND = SoundEvents.ENTITY_PLAYER_HURT;

    TeleportSpell() {
        super("Teleport", 20, 60, JMagic.id("hud/icon/teleport"));
    }

    @Override
    protected void performCast(ServerWorld world, Entity user) {
        Vec3d lookingAtPos = JSpellClientHelper.GetLookingAt(user, MAX_DISTANCE, true);

        if (world instanceof ServerWorld serverWorld) {
            Entity teleportedCaster = user.teleportTo(new TeleportTarget(serverWorld, lookingAtPos, user.getVelocity(), user.getYaw(), user.getPitch(), TeleportTarget.NO_OP));
            if (teleportedCaster != null) {
                user.setVelocity(0.0, 0.0, 0.0);
                user.velocityModified = true;
                teleportedCaster.onLanding();
                createParticleCircle(world, ParticleTypes.DRAGON_BREATH, lookingAtPos, 0.75, 32);
            }
        }
    }

    private void createParticleCircle(ServerWorld world, ParticleEffect particle, Vec3d position, double radius, int particleAmount) {
        world.spawnParticles(particle,
                position.getX(), position.getY() + 0.15, position.getZ(),
                1, 0,
                0, 0, 0);
    }
}
