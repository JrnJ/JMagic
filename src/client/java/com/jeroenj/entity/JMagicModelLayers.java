package com.jeroenj.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public final class JMagicModelLayers {
    public static final EntityModelLayer METEOR = new EntityModelLayer(JMagicEntities.METEOR_ENTITY, "main");
    public static final EntityModelLayer MANA_BOLT = new EntityModelLayer(JMagicEntities.MANA_BOLT_ENTITY, "main");

    public static void initialize() {
        EntityModelLayerRegistry.registerModelLayer(METEOR, MeteorEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MANA_BOLT, ManaBoltEntityModel::getTexturedModelData);
    }
}
