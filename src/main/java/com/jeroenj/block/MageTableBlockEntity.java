package com.jeroenj.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class MageTableBlockEntity extends BlockEntity {
    public MageTableBlockEntity(BlockPos pos, BlockState state) {
        super(JMagicBlockEntityTypes.MAGE_TABLE_BLOCK, pos, state);
    }
}
