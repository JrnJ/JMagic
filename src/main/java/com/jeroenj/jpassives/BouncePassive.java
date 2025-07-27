package com.jeroenj.jpassives;

import com.jeroenj.util.JHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class BouncePassive extends JMagicPassive {
    public static final float LEAP_STRENGTH = 0.675f;

    @Override
    public void onActivate(ServerPlayerEntity player) {
        if (player.fallDistance >= 5.0f) {
            Vec3d vec3d = player.getVelocity();
            player.setVelocity(vec3d.x, 0.5f, vec3d.z);
            player.velocityModified = true;

            // Small look-direction boost feels better
            Vec3d lookDirection = player.getRotationVec(1.0f);
            Vec3d leapVelocity = new Vec3d(
                    lookDirection.x * LEAP_STRENGTH,
                    0.0f,
                    lookDirection.z * LEAP_STRENGTH
            );
            player.addVelocity(leapVelocity);
            player.velocityModified = true;

            JHelper.playServerSound(player, SoundEvents.BLOCK_SLIME_BLOCK_FALL, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }
}
