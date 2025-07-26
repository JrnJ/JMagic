package com.jeroenj.entity;

import com.jeroenj.JMagic;
import com.jeroenj.entity.kitsune.KitsuneEntityModel;
import com.jeroenj.model.KitsuneTailEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public final class JMagicModelLayers {
    public static final EntityModelLayer METEOR = new EntityModelLayer(JMagicEntities.METEOR_ENTITY, "main");
    public static final EntityModelLayer MANA_BOLT = new EntityModelLayer(JMagicEntities.MANA_BOLT_ENTITY, "main");
    public static final EntityModelLayer KITSUNE = new EntityModelLayer(JMagicEntities.KITSUNE_ENTITY, "main");

    //
    public static final EntityModelLayer KITSUNE_TAIL = new EntityModelLayer(JMagic.id("kitsune_tail"), "main");

    public static void initialize() {
        EntityModelLayerRegistry.registerModelLayer(METEOR, MeteorEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MANA_BOLT, ManaBoltEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(KITSUNE, KitsuneEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(KITSUNE_TAIL, KitsuneTailEntityModel::getTexturedModelData);
    }
}
