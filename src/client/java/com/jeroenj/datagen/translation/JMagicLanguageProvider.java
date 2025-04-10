package com.jeroenj.datagen.translation;

import com.jeroenj.JMagic;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public abstract class JMagicLanguageProvider extends FabricLanguageProvider {
    protected static String Key(String name) { return "key." + JMagic.MOD_ID + "." + name; }

    protected static String ItemGroup(String name) {
        return "itemGroup." + JMagic.MOD_ID + "." + name;
    }

    protected static String Category(String name) {
        return "category." + JMagic.MOD_ID + "." + name;
    }

    protected JMagicLanguageProvider(FabricDataOutput dataOutput, String languageCode, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, languageCode, registryLookup);
    }

    @Override
    public abstract void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder);
}
