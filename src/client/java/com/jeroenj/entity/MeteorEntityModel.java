package com.jeroenj.entity;

import com.jeroenj.JMagic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class MeteorEntityModel extends Model {
    public static final Identifier TEXTURE = JMagic.id("textures/entity/meteor.png");

    public MeteorEntityModel(ModelPart root, Function<Identifier, RenderLayer> layerFactory) {
        super(root, layerFactory);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData meteor = modelPartData.addChild("meteor", ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-7.0F, -15.0F, -7.0F, 14.0F, 14.0F, 14.0F,
                        new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
