package com.jeroenj.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class GlutinousRiceSoakerBlockEntity extends BlockEntity {
    public GlutinousRiceSoakerBlockEntity(BlockPos pos, BlockState state) {
        super(JMagicBlockEntityTypes.GLUTINOUS_RICE_SOAKER_BLOCK, pos, state);
    }
}
