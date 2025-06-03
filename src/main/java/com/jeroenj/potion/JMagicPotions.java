package com.jeroenj.potion;

import com.jeroenj.JMagic;
import com.jeroenj.effect.JMagicEffects;
import com.jeroenj.item.JMagicItems;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class JMagicPotions {

    public static final Potion KITSUNE_FLAMES_POTION =
            Registry.register(Registries.POTION, JMagic.id("kitsune_flames"),
                    new Potion("kitsune_flames",
                            new StatusEffectInstance(
                                    JMagicEffects.KITSUNE_FLAMES,
                                    40,
                                    0)));

    public static void initialize() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(
                    Potions.WATER, // Input
                    JMagicItems.KITSUNE_FLAME, // Ingredient
                    Registries.POTION.getEntry(KITSUNE_FLAMES_POTION) // Output
            );
        });
    }
}
