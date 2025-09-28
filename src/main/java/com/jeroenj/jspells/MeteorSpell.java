package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import com.jeroenj.entity.JMagicEntities;
import com.jeroenj.entity.MeteorEntity;
import com.jeroenj.util.JHelper;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class MeteorSpell extends JSpell {
    public static final double MAX_DISTANCE = 25.0;
    private static final double OFFSET_RADIUS = 5.0;
    private static final double FALL_SPEED = -0.5;
    public static final SoundEvent SUMMON_SOUND = SoundEvents.ENTITY_PLAYER_HURT;

    MeteorSpell() {
        super(JMagicJSpells.METEOR_SPELL, "Meteor", "Cast a large Meteor!", 20, 60, JMagic.id("hud/icon/meteor"));
    }

    @Override
    protected void serverCast(ServerWorld world, ServerPlayerEntity user) {
        double ADDED_HEIGHT = 15.0;
        double METEOR_SPEED = 0.5;

        Vec3d lookingAtPos = JSpellClientHelper.getLookingAt(user, MAX_DISTANCE, true);
        BlockPos blockBelow = JHelper.getClosestBlockBelow(lookingAtPos, world, MAX_DISTANCE);

        MeteorEntity meteor = new MeteorEntity(JMagicEntities.METEOR, world);
        // The block goes at an angle if there is ground (looks cooler), otherwise it goes straight down
        if (blockBelow == null) {
            meteor.start(lookingAtPos.add(ADDED_HEIGHT), new Vec3d(0.0, -0.5, 0.0));
        } else {
            Random random = new Random();

            double angle = random.nextDouble() * 2 * Math.PI;
            double distance = Math.sqrt(random.nextDouble()) * OFFSET_RADIUS; // sqrt ensures uniform distribution

            // Convert polar coordinates to Cartesian coordinates
            double x = Math.cos(angle) * distance;
            double z = Math.sin(angle) * distance;

            Vec3d targetPos = blockBelow.toCenterPos().add(new Vec3d(x, ADDED_HEIGHT, z));

            // Calculate the direction vector to the target
            Vec3d direction = targetPos.subtract(meteor.getPos()).normalize();

            // Apply speed to the horizontal direction
            double horizontalSpeed = 0.5; // adjust this value for desired horizontal speed
            Vec3d horizontalVelocity = direction.multiply(horizontalSpeed);

            // Add downward velocity component (falling effect)
            double verticalSpeed = -0.5; // negative for downward movement
            Vec3d velocity = horizontalVelocity.add(new Vec3d(0.0, verticalSpeed, 0.0));

            meteor.start(targetPos, velocity);
        }
        world.spawnEntity(meteor);

        JHelper.playServerSound(user, SUMMON_SOUND, 1.0f, 1.0f);
        JHelper.spawnServerParticle(world, ParticleTypes.CRIT, lookingAtPos, 3, 0.0, 0.0, 0.0, 0.0);
    }
}
