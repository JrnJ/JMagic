package com.jeroenj.featurerender;

import com.jeroenj.JMagic;
import com.jeroenj.entity.JMagicModelLayers;
import com.jeroenj.jspells.ToggleSunGodSpell;
import com.jeroenj.model.SunGodAuraEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SunGodAuraFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    public static final Identifier[] TEXTURE_FRAMES = new Identifier[]{
            JMagic.id("textures/sun_god_aura_1.png"),
            JMagic.id("textures/sun_god_aura_2.png"),
            JMagic.id("textures/sun_god_aura_3.png"),
            JMagic.id("textures/sun_god_aura_4.png")
    };
    private final SunGodAuraEntityModel<PlayerEntityRenderState> model;

    public SunGodAuraFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context, LoadedEntityModels modelLoader) {
        super(context);
        this.model = new SunGodAuraEntityModel<>(modelLoader.getModelPart(JMagicModelLayers.SUN_GOD_AURA));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityRenderState state, float limbAngle, float limbDistance) {
        if (state.invisible || !ToggleSunGodSpell.active) {
            return;
        }

        matrices.push();

        int frameIndex = ((int)(state.age / 6)) % TEXTURE_FRAMES.length;
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE_FRAMES[frameIndex]));
        this.model.setAngles(state);
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
    }
}
