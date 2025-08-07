package com.jeroenj.jpassives;

import com.jeroenj.jparticle.GroundSlamParticleEffect;
import com.jeroenj.sound.JMagicSounds;
import com.jeroenj.util.JHelper;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Box;

public class GroundSlamPassive extends JMagicPassive {
    public static final float SLAM_RADIUS = 2.5f;
    private static final float HALF_SLAM_DEPTH = 2.5f;
    private static final float DAMAGE = 10.0f;

    @Override
    public void onActivate(ServerPlayerEntity player) {
        if (player.fallDistance >= 10.0f) {
            ServerWorld serverWorld = player.getServerWorld();
            JHelper.playServerSound(player, JMagicSounds.GROUND_IMPACT, SoundCategory.PLAYERS, 0.5f, 1.0f);

            Box aoeBox = new Box(
                    player.getX() - SLAM_RADIUS, player.getY() - HALF_SLAM_DEPTH, player.getZ() - SLAM_RADIUS,
                    player.getX() + SLAM_RADIUS, player.getY() + HALF_SLAM_DEPTH, player.getZ() + SLAM_RADIUS
            );

            for (MobEntity mob : serverWorld.getEntitiesByClass(MobEntity.class, aoeBox, e -> e != null && e.isAlive())) {
                mob.damage(serverWorld, player.getDamageSources().generic(), DAMAGE);
            }

            new GroundSlamParticleEffect().activate(player);
        }
    }
}
