package com.jeroenj.world.biome;

import com.jeroenj.JMagic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

// Look at "OverworldBiomeCreator"
public class JMagicBiomes {
    public static final RegistryKey<Biome> MAGICAL_FOREST_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            JMagic.id("magical_forest"));

    public static void bootstrap(Registerable<Biome> context) {
        context.register(MAGICAL_FOREST_BIOME, magicalForestBiome(context));
    }

    // From "OverworldBiomeCreator" class
    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    public static Biome magicalForestBiome(Registerable<Biome> context) {
        // Mobs
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PIG, 2, 3, 5));
        DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);

        // Features
        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        // 1. Flowers?
        DefaultBiomeFeatures.addForestFlowers(biomeBuilder);

        // 2. Trees
        DefaultBiomeFeatures.addForestTrees(biomeBuilder);

        // 3. Vegetation
        DefaultBiomeFeatures.addDefaultFlowers(biomeBuilder);
        DefaultBiomeFeatures.addForestGrass(biomeBuilder);
        DefaultBiomeFeatures.addDefaultMushrooms(biomeBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(biomeBuilder);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.9f)
                .temperature(0.95f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x3F76E4)
                        .waterFogColor(0x50533)
                        .skyColor(0x77A8FF)
                        .grassColor(0x59C93C)
                        .foliageColor(0x30BB0B)
                        .fogColor(0x00C0D8FF)
                        .moodSound(BiomeMoodSound.CAVE).build())
                .build();
    }
}
