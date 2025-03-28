package com.jeroenj.model;

import com.jeroenj.item.MagicWand;
import com.mojang.serialization.*;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

// https://github.com/Nic4Las/Minecraft-Enderite-Mod/tree/Fabric-1.20
public class MagicWandItemRenderer extends MapCodec<SpecialModelRenderer.Unbaked> implements SpecialModelRenderer<MagicWand> {
    @Override
    public void render(@Nullable MagicWand data, ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {

    }

    @Nullable
    @Override
    public MagicWand getData(ItemStack stack) {
        return null;
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
        return null;
    }

    @Override
    public <T> DataResult<Unbaked> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        return null;
    }

    @Override
    public <T> RecordBuilder<T> encode(Unbaked unbaked, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
        return null;
    }
}
