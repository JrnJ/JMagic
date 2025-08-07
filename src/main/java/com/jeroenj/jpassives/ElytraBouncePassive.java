package com.jeroenj.jpassives;

import com.jeroenj.sound.JMagicSounds;
import com.jeroenj.util.JHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

public class ElytraBouncePassive extends JMagicPassive {
    @Override
    public void onActivate(ServerPlayerEntity player) {
        JHelper.playServerSound(
                player, JMagicSounds.CARTOON_BOING, SoundCategory.PLAYERS,
                0.5f, 0.9f + (player.getServerWorld().getRandom().nextFloat() * 0.1f));
    }
}
