package com.jeroenj;

import com.jeroenj.datagen.JMagicModelGenerator;
import com.jeroenj.datagen.JMagicWorldGenerator;
import com.jeroenj.datagen.translation.JMagicEnUsLangProvider;
import com.jeroenj.world.biome.JMagicBiomes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class JMagicDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(JMagicModelGenerator::new);
		pack.addProvider(JMagicEnUsLangProvider::new);
		pack.addProvider(JMagicWorldGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.BIOME, JMagicBiomes::bootstrap);
	}
}
