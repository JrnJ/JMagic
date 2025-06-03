package com.jeroenj.entity;

import com.jeroenj.JMagic;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class ManaBoltEntityRenderer extends JEntityRenderer<ManaBoltEntity> {
    public static final Identifier TEXTURE = JMagic.id("textures/entity/mana_bolt.png");
    private final ManaBoltEntityModel model;

    public ManaBoltEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        model = new ManaBoltEntityModel(context.getPart(JMagicModelLayers.MANA_BOLT), this::getRenderLayer);
    }

    @Override
    public void render(JEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(state.yaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(state.pitch));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(model.getLayer(TEXTURE));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();

        super.render(state, matrices, vertexConsumers, light);
    }

    @Override
    protected RenderLayer getRenderLayer(Identifier texture) {
        return RenderLayer.getEntityTranslucent(texture);
    }
}
