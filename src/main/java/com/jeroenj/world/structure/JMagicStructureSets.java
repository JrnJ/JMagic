package com.jeroenj.world.structure;

import com.jeroenj.JMagic;
import com.jeroenj.world.biome.JMagicBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;
import net.minecraft.world.gen.GenerationStep;

public class JMagicStructureSets {
//    public static final RegistryKey<StructureSet> KITSUNE_TEMPLE = RegistryKey.of(
//            RegistryKeys.STRUCTURE_SET, JMagic.id("kitsune_temple"));

    public static void initialize() {
//        BiomeModifications.addFeature(
//                context -> context.getBiomeKey().equals(JMagicBiomes.MAGICAL_FOREST_BIOME),
//                GenerationStep.Feature.SURFACE_STRUCTURES,
//                RegistryKey.of(RegistryKeys.PLACED_FEATURE, JMagic.id("kitsune_temple"))
//        );
    }
}
