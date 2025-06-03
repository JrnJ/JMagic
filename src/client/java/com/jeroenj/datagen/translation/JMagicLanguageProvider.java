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
    protected static void StatusEffectWithPotion(TranslationBuilder translationBuilder, String key, String name) {
        translationBuilder.add("effect." + JMagic.MOD_ID + "." + key, name);
        translationBuilder.add("item.minecraft.potion.effect" + key, "Potion of " + name);
        translationBuilder.add("item.minecraft.splash_potion.effect" + key, "Splash Potion of " + name);
        translationBuilder.add("item.minecraft.lingering_potion.effect" + key, "Lingering Potion of " + name);
        translationBuilder.add("item.minecraft.tipped_arrow.effect" + key, "Arrow of " + name);
    }

    protected JMagicLanguageProvider(FabricDataOutput dataOutput, String languageCode, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, languageCode, registryLookup);
    }

    @Override
    public abstract void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder);
}
