package com.jeroenj.jparticle;

import net.minecraft.server.network.ServerPlayerEntity;

public abstract class JParticleEffect {
    public abstract void activate(ServerPlayerEntity player);
}
