package com.jeroenj.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ManaBoltEntityModel extends Model {
    public ManaBoltEntityModel(ModelPart root, Function<Identifier, RenderLayer> layerFactory) {
        super(root, layerFactory);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData entity = modelPartData.addChild("mana_bolt", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 8.0F,
                        new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
}
