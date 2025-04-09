package com.jeroenj.entity.Fairy;

import com.jeroenj.JMagic;
import com.jeroenj.entity.FairyEntity;
import com.jeroenj.entity.JMagicModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class FairyEntityRenderer extends MobEntityRenderer<FairyEntity, FairyEntityRenderState, FairyEntityModel> {
    private static final Identifier TEXTURE = JMagic.id("textures/entity/fairy/fairy.png");

    public FairyEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FairyEntityModel(context.getPart(JMagicModelLayers.FAIRY)), 0.4F);
        this.addFeature(new HeldItemFeatureRenderer<>(this));
    }

    public Identifier getTexture(FairyEntityRenderState fairyEntityRenderState) {
        return TEXTURE;
    }

    public FairyEntityRenderState createRenderState() {
        return new FairyEntityRenderState();
    }

    public void updateRenderState(FairyEntity fairyEntity, FairyEntityRenderState fairyEntityRenderState, float f) {
        super.updateRenderState(fairyEntity, fairyEntityRenderState, f);
        ArmedEntityRenderState.updateRenderState(fairyEntity, fairyEntityRenderState, this.itemModelResolver);
        fairyEntityRenderState.dancing = fairyEntity.isDancing();
        fairyEntityRenderState.spinning = fairyEntity.isSpinning();
        fairyEntityRenderState.spinningAnimationTicks = fairyEntity.getSpinningAnimationTicks(f);
        fairyEntityRenderState.itemHoldAnimationTicks = fairyEntity.getItemHoldAnimationTicks(f);
    }

    protected int getBlockLight(FairyEntity fairyEntity, BlockPos blockPos) {
        return 15;
    }
}
