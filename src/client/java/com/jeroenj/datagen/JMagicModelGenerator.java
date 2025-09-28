package com.jeroenj.datagen;

import com.jeroenj.block.JMagicBlocks;
import com.jeroenj.block.crop.GlutinousRiceCropBlock;
import com.jeroenj.item.JMagicItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class JMagicModelGenerator extends FabricModelProvider {
    public JMagicModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
//        blockStateModelGenerator.registerSimpleCubeAll(JmagicBlocks.YOUR_BLOCK);
        generator.registerCrop(JMagicBlocks.GLUTINOUS_RICE_CROP_BLOCK, GlutinousRiceCropBlock.AGE, 0, 1, 2, 3, 4, 5, 6, 7);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(JMagicItems.SPELL_SCROLL, Models.GENERATED);
        generator.register(JMagicItems.GOMU_GOMU_NO_MI, Models.GENERATED);

//        generator.register(JMagicItems.GLUTINOUS_RICE_SEEDS, Models.GENERATED);
        generator.register(JMagicItems.GLUTINOUS_RICE, Models.GENERATED);
        generator.register(JMagicItems.MOCHI_MIXTURE, Models.GENERATED);
        generator.register(JMagicItems.MOCHI_ITEM, Models.GENERATED);
        generator.register(JMagicItems.GREEN_TEA_MOCHI_ITEM, Models.GENERATED);
        generator.register(JMagicItems.STRAWBERRY_MOCHI_ITEM, Models.GENERATED);
        generator.register(JMagicItems.CHOCOLATE_MOCHI_ITEM, Models.GENERATED);
        generator.register(JMagicItems.STRAWBERRY_ITEM, Models.GENERATED);
    }
}
