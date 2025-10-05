package com.jeroenj.jpassives;

import com.jeroenj.sound.JMagicSounds;
import com.jeroenj.util.JHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

public class BouncePassive extends JMagicPassive {
    public static final float LEAP_STRENGTH = 0.675f;
    public boolean playSound = true;

    @Override
    public void onActivate(ServerPlayerEntity player) {
        if (player.fallDistance >= 5.0f) {
            if (player.isSneaking()) return;

            Vec3d vec3d = player.getVelocity();
            player.setVelocity(vec3d.x, 0.75f, vec3d.z);
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

            if (playSound) {
                JHelper.playServerSound(
                        player, JMagicSounds.CARTOON_BOING, SoundCategory.PLAYERS,
                        1.0f, 0.9f + (player.getServerWorld().getRandom().nextFloat() * 0.1f));
            }
        }
    }
}
