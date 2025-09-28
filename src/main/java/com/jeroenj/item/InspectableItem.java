package com.jeroenj.item;

import com.jeroenj.sound.JMagicSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;

public class InspectableItem extends Item {
    private boolean inspecting = false;

    public InspectableItem(Settings settings) {
        super(settings);
    }

    public void clientInspect(PlayerEntity player) {
        inspecting = true;
    }
}
