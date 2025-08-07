package com.jeroenj.datagen;

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
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//        blockStateModelGenerator.registerSimpleCubeAll(JmagicBlocks.YOUR_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(JMagicItems.SPELL_SCROLL, Models.GENERATED);
        itemModelGenerator.register(JMagicItems.GOMU_GOMU_NO_MI, Models.GENERATED);
    }
}
