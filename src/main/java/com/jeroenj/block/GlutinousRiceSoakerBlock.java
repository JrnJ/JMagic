package com.jeroenj.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GlutinousRiceSoakerBlock extends BlockWithEntity {
    public static final MapCodec<GlutinousRiceSoakerBlock> CODEC = createCodec(GlutinousRiceSoakerBlock::new);

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    protected GlutinousRiceSoakerBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GlutinousRiceSoakerBlockEntity(pos, state);
    }
}
