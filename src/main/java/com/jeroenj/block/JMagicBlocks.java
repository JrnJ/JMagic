package com.jeroenj.block;

import com.jeroenj.JMagic;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public final class JMagicBlocks {

    public static final Block MAGE_TABLE_BLOCK = register("mage_table", Block::new, AbstractBlock.Settings.create()
            .strength(4.0f));

    public static void initialize() {

    }

    private static Block register(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, JMagic.id(path));

        final Block block = Blocks.register(registryKey, factory, settings);
        Items.register(block);

        return block;
    }
}
