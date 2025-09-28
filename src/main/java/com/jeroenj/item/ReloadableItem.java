package com.jeroenj.item;

import net.minecraft.entity.player.PlayerEntity;

public interface ReloadableItem {
    void reload(PlayerEntity player);
}
