package com.jeroenj.mixin.client;

import com.jeroenj.featurerender.KitsuneTailFeatureRenderer;
import com.jeroenj.rendering.CustomFeatureRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(at = @At(value = "TAIL"), method = "<init>")
    private void init(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
//        this.addFeature(new CustomFeatureRenderer(this));
        this.addFeature(new KitsuneTailFeatureRenderer(this, ctx.getEntityModels()));
    }
}
