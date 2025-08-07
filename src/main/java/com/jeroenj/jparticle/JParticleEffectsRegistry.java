package com.jeroenj.jparticle;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class JParticleEffectsRegistry {
    private static final Map<Identifier, Supplier<JParticleEffect>> PARTICLE_EFFECT_FACTORY = new HashMap<>();

    public static void register(Identifier identifier, Supplier<JParticleEffect> particleEffect) {
        PARTICLE_EFFECT_FACTORY.put(identifier, particleEffect);
    }

    public static JParticleEffect getParticleEffect(Identifier identifier) {
        return PARTICLE_EFFECT_FACTORY.get(identifier).get();
    }
}
