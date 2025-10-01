package com.jeroenj.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

@Environment(EnvType.SERVER)
public final class JHelper {

    // Player
//    public static Vec3d getLookDirection(ServerPlayerEntity player) {
//      player.getRotationVec(1.0f);
//    }

    // World
    public static BlockPos getClosestBlockBelow(Vec3d position, World world, double maxRange) {
        // Starting at the given position, move downwards in the Y-axis to find the closest block
        for (double y = position.y; y >= position.y - maxRange; y -= 0.5) {  // Check every half block
            BlockPos blockPos = new BlockPos((int)position.x, (int)y, (int)position.z);
            if (!world.getBlockState(blockPos).isAir()) {  // If the block is not air, return it
                return blockPos;
            }
        }
        return null;
    }

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

    public static void playServerSound(ServerPlayerEntity player, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        ServerWorld world = player.getServerWorld();
        world.playSound(null, player.getX(), player.getY(), player.getZ(), sound, category, volume, pitch);
    }

    public static void playClientSound(Entity entity, SoundEvent sound, float volume, float pitch) {
        entity.playSound(sound, volume, pitch);
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
