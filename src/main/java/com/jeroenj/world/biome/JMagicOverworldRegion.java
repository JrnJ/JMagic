package com.jeroenj.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class JMagicOverworldRegion extends Region {
    public JMagicOverworldRegion(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, modifiedVanillaOverworldBuilder -> {
            // This replaces SOME forest biomes which is NOT ideal, there is a better fix for this
            // There are example on Terrablender's GitHub
            modifiedVanillaOverworldBuilder.replaceBiome(BiomeKeys.FOREST, JMagicBiomes.MAGICAL_FOREST_BIOME);
        });
    }
}
