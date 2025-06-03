package com.jeroenj.featurerender;

import com.jeroenj.JMagic;
import com.jeroenj.entity.JMagicModelLayers;
import com.jeroenj.model.KitsuneTailEntityModel;
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
public class KitsuneTailFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    public static final Identifier TEXTURE = JMagic.id("textures/kitsune_tail.png");
    private final KitsuneTailEntityModel<PlayerEntityRenderState> model;

    public KitsuneTailFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context, LoadedEntityModels modelLoader) {
        super(context);
        this.model = new KitsuneTailEntityModel<>(modelLoader.getModelPart(JMagicModelLayers.KITSUNE_TAIL));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityRenderState state, float limbAngle, float limbDistance) {
        if (state.invisible) {
            return;
        }

        matrices.push();

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(TEXTURE));
        this.model.setAngles(state);
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }
}
