package com.jeroenj.entity;

import com.jeroenj.JMagic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public final class JMagicModelLayers {
    public static final EntityModelLayer METEOR = new EntityModelLayer(JMagic.id("meteor"), "main");

    public static void initialize() {
        EntityModelLayerRegistry.registerModelLayer(METEOR, MeteorEntityModel::getTexturedModelData);
    }
}
