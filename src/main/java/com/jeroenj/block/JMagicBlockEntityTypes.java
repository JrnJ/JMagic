package com.jeroenj.block;

import com.jeroenj.JMagic;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class JMagicBlockEntityTypes {
    public static final BlockEntityType<MageTableBlockEntity> MAGE_TABLE_BLOCK = register(
            "mage_table",
            FabricBlockEntityTypeBuilder.create(MageTableBlockEntity::new, JMagicBlocks.MAGE_TABLE_BLOCK).build()
    );

    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, JMagic.id(path), blockEntityType);
    }

    public static void initialize() {

    }
}
