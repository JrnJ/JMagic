package com.jeroenj.particles;

import com.jeroenj.JMagic;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public final class JMagicParticles {
    public static final SimpleParticleType MAGIC_PARTICLE = FabricParticleTypes.simple();

//    private static void register(String path) {
//        final RegistryKey<ParticleType<?>> registryKey = RegistryKey.of(RegistryKeys.PARTICLE_TYPE, JMagic.id(path));
//    }

    public static void initialize() {
        Registry.register(Registries.PARTICLE_TYPE, JMagic.id("magic_particle"), MAGIC_PARTICLE);
    }
}
