package com.jeroenj.entity;

import com.jeroenj.JMagic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class MeteorEntityRenderer extends EntityRenderer<MeteorEntity, MeteorEntityRenderState> {
    public static final Identifier TEXTURE = JMagic.id("textures/entity/meteor.png");
    private final MeteorEntityModel model;

    public MeteorEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new MeteorEntityModel(context.getPart(JMagicModelLayers.METEOR), this::getRenderLayer);
    }

    @Override
    public void render(MeteorEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(state.pitch));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(model.getLayer(TEXTURE));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();

        super.render(state, matrices, vertexConsumers, light);
    }

    protected RenderLayer getRenderLayer(Identifier texture) {
        return RenderLayer.getEntityTranslucent(texture);
    }

    @Override
    public MeteorEntityRenderState createRenderState() {
        return new MeteorEntityRenderState();
    }

    @Override
    public void updateRenderState(MeteorEntity entity, MeteorEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.yaw = entity.getLerpedYaw(tickDelta);
        state.pitch = entity.getLerpedPitch(tickDelta);
    }
}
