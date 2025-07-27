package com.jeroenj.jpassives;

import net.minecraft.server.network.ServerPlayerEntity;

public abstract class JMagicPassive {
    public abstract void onActivate(ServerPlayerEntity player);
}
