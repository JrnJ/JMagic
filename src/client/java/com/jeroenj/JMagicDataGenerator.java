package com.jeroenj;

import com.jeroenj.datagen.JMagicModelGenerator;
import com.jeroenj.datagen.translation.JMagicEnUsLangProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class JMagicDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(JMagicModelGenerator::new);

		// Translations
		pack.addProvider(JMagicEnUsLangProvider::new);
	}
}
