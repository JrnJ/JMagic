package com.jeroenj.jparticle;

import com.jeroenj.jpassives.GroundSlamPassive;
import net.minecraft.block.BlockState;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class GroundSlamParticleEffect extends JParticleEffect {

    @Override
    public void activate(ServerPlayerEntity player) {
        World world = player.getServerWorld();
        BlockState blockState = world.getBlockState(player.getBlockPos());
        for (int z = 0; z < 5; z++) {
            for (int x = 0; x < 5; x++) {
                double px = player.getX() - GroundSlamPassive.SLAM_RADIUS + x;
                double pz = player.getZ() - GroundSlamPassive.SLAM_RADIUS + z;

                world.addParticle(
                        new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState),
                        px, player.getY(), pz,
                        0.0, 0.1, 0.0
                );
            }
        }
    }
}
