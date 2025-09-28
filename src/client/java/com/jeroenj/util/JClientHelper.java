package com.jeroenj.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;

public class JClientHelper {
    public static void playClientSound(PlayerEntity player, SoundEvent sound, float volume, float pitch) {
        player.playSound(sound, volume, pitch);
    }
}
