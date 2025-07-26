package com.jeroenj.entity.kitsune;

import com.jeroenj.JMagic;
import com.jeroenj.entity.JMagicModelLayers;
import com.jeroenj.entity.KitsuneEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class KitsuneEntityRenderer extends MobEntityRenderer<KitsuneEntity, KitsuneEntityRenderState, KitsuneEntityModel> {
    public static final Identifier TEXTURE = JMagic.id("textures/entity/kitsune.png");
    public static final float SHADOW_RADIUS = 1.0f;

    public KitsuneEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new KitsuneEntityModel(context.getPart(JMagicModelLayers.KITSUNE)), SHADOW_RADIUS);
    }

    @Override
    public Identifier getTexture(KitsuneEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public KitsuneEntityRenderState createRenderState() {
        return new KitsuneEntityRenderState();
    }

    @Override
    public void updateRenderState(KitsuneEntity kitsuneEntity, KitsuneEntityRenderState kitsuneEntityRenderState, float f) {
        super.updateRenderState(kitsuneEntity, kitsuneEntityRenderState, f);
        // Check "WardenEntityRenderer"
    }
}
