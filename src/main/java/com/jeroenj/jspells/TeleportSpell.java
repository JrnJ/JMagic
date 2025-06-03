package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;

public class TeleportSpell extends JSpell {
    public static final float MAX_DISTANCE = 20.0f;
    public static final SoundEvent CAST_SOUND = SoundEvents.ENTITY_PLAYER_HURT;

    TeleportSpell() {
        super(JMagicJSpells.TELEPORT_SPELL, "Teleport", "Teleport up to 20 blocks in the direction you look at.", 20, 60, JMagic.id("hud/icon/teleport"));
    }

    @Override
    protected void performCast(ServerWorld world, ServerPlayerEntity user) {
        Vec3d lookingAtPos = JSpellClientHelper.getLookingAt(user, MAX_DISTANCE, true);

        if (world instanceof ServerWorld serverWorld) {
            Entity teleportedCaster = user.teleportTo(new TeleportTarget(serverWorld, lookingAtPos, user.getVelocity(), user.getYaw(), user.getPitch(), TeleportTarget.NO_OP));
            if (teleportedCaster != null) {
                user.setVelocity(user.getVelocity().getX(), 0.0, user.getVelocity().getZ());
                user.velocityModified = true;
                teleportedCaster.onLanding();
                createParticleCircle(world, ParticleTypes.DRAGON_BREATH, lookingAtPos, 1.0, 24);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_PLAYER_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        }
    }

    private void createParticleCircle(ServerWorld world, ParticleEffect particle, Vec3d position, double radius, int particleAmount) {
        double angleStep = 2 * Math.PI / particleAmount;  // Divide the circle into equal parts

        for (int i = 0; i < particleAmount; i++) {
            double angle = i * angleStep;
            double xOffset = radius * Math.cos(angle);
            double zOffset = radius * Math.sin(angle);

            // Spawn the particle at the calculated position
            world.spawnParticles(particle,
                    position.getX() + xOffset, position.getY() + 0.1, position.getZ() + zOffset,
                    1,
                    0, 0, 0,
                    0);
        }
    }
}
