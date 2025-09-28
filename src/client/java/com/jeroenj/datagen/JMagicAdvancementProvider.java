package com.jeroenj.datagen;

import com.jeroenj.JMagic;
import com.jeroenj.item.JMagicItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class JMagicAdvancementProvider extends FabricAdvancementProvider {
    public JMagicAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry root = Advancement.Builder.create()
                .display(
                        JMagicItems.MOCHI_ITEM,
                        Text.literal("JMagic"),
                        Text.literal("The magical farming begins!"),
                        Identifier.ofVanilla("textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_glutinous_rice_seeds", InventoryChangedCriterion.Conditions.items(JMagicItems.GLUTINOUS_RICE_SEEDS))
                .build(consumer, JMagic.MOD_ID + ":root");

        AdvancementEntry gotMochi = Advancement.Builder.create().parent(root)
                .display(
                        JMagicItems.MOCHI_ITEM,
                        Text.literal("Your first Mochi"),
                        Text.literal("Don't eat it just yet!"),
                        Identifier.ofVanilla("textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_mochi", InventoryChangedCriterion.Conditions.items(JMagicItems.MOCHI_ITEM))
                .build(consumer, JMagic.MOD_ID + ":got_mochi");

        AdvancementEntry gotAllMochi = Advancement.Builder.create().parent(gotMochi)
                .display(
                        JMagicItems.GREEN_TEA_MOCHI_ITEM,
                        Text.literal("All the Mochi"),
                        Text.literal("You crafted every Mochi!"),
                        Identifier.ofVanilla("textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("got_all_mochi", InventoryChangedCriterion.Conditions.items(
                        JMagicItems.MOCHI_ITEM,
                        JMagicItems.CHOCOLATE_MOCHI_ITEM,
                        JMagicItems.STRAWBERRY_MOCHI_ITEM,
                        JMagicItems.GREEN_TEA_MOCHI_ITEM))
                .build(consumer, JMagic.MOD_ID + ":got_all_mochi");
    }
}
