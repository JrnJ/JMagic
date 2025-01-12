package com.jeroenj.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public final class JHelper {

    // Particles
    public static void spawnServerParticle(World world, ParticleEffect particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double speed) {
        if (isClient(world)) return;

        ServerWorld serverWorld = (ServerWorld) world;
        serverWorld.spawnParticles(
                particle,
                x, y, z,
                count,
                offsetX, offsetY, offsetZ,
                speed
        );
    }

    public static void spawnServerParticle(World world, ParticleEffect particle, Vec3d position, int count, double offsetX, double offsetY, double offsetZ, double speed) {
        spawnServerParticle(world, particle, position.getX(), position.getY(), position.getZ(), count, offsetX, offsetY, offsetZ, speed);
    }

    // Sounds
    public static void playServerSound(Entity entity, SoundEvent sound, float volume, float pitch) {
        entity.playSound(sound, volume, pitch);
    }
    public static void playServerSound(PlayerEntity player, SoundEvent sound, float volume, float pitch) {
        player.playSound(sound, volume, pitch);
    }

    // Private Helpers
    private static boolean isClient(World world) {
        if (world.isClient()) {
            System.err.println("[JMagic::JHelper] " + "world is client.");
            return true;
        }

        return false;
    }
}
