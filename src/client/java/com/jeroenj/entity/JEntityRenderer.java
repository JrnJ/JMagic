package com.jeroenj.entity;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public abstract class JEntityRenderer<T extends Entity> extends EntityRenderer<T, JEntityRenderState> {
    protected JEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public JEntityRenderState createRenderState() {
        return new JEntityRenderState();
    }

    @Override
    public void updateRenderState(T entity, JEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.yaw = entity.getLerpedYaw(tickDelta);
        state.pitch = entity.getLerpedPitch(tickDelta);
    }

    protected abstract RenderLayer getRenderLayer(Identifier texture);
}
