package com.jeroenj.block;

import com.jeroenj.JMagic;
import com.jeroenj.block.crop.GlutinousRiceCropBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public final class JMagicBlocks {

    public static final Block MAGE_TABLE_BLOCK
            = register("mage_table", Block::new, AbstractBlock.Settings.create().strength(4.0f), true);

    public static final Block GLUTINOUS_RICE_SOAKER_BLOCK
            = register("glutinous_rice_soaker", GlutinousRiceSoakerBlock::new, AbstractBlock.Settings.create().strength(4.0f), true);

    public static final GlutinousRiceCropBlock GLUTINOUS_RICE_CROP_BLOCK
            = register("glutinous_rice", GlutinousRiceCropBlock::new, AbstractBlock.Settings.copy(Blocks.WHEAT), false);

    public static void initialize() {

    }

    private static <T extends Block> T register(
            String name,
            Function<AbstractBlock.Settings, T> blockFactory,
            AbstractBlock.Settings settings,
            boolean shouldRegisterItem) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        T block = blockFactory.apply(settings.registryKey(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = keyOfItem(name);
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, JMagic.id(name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, JMagic.id(name));
    }
}
