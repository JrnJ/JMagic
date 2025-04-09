package com.jeroenj.entity.Fairy;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class FairyEntityModel extends EntityModel<FairyEntityRenderState> implements ModelWithArms {

    private final ModelPart head = this.root.getChild(EntityModelPartNames.HEAD);
    private final ModelPart body = this.root.getChild(EntityModelPartNames.BODY);
    private final ModelPart rightArm = this.body.getChild(EntityModelPartNames.RIGHT_ARM);
    private final ModelPart leftArm = this.body.getChild(EntityModelPartNames.LEFT_ARM);
    private final ModelPart rightWing = this.body.getChild(EntityModelPartNames.RIGHT_WING);
    private final ModelPart leftWing = this.body.getChild(EntityModelPartNames.LEFT_WING);
    private static final float field_38999 = (float) (Math.PI / 4);
    private static final float field_39000 = -1.134464F;
    private static final float field_39001 = (float) (-Math.PI / 3);

    public FairyEntityModel(ModelPart modelPart) {
        super(modelPart.getChild(EntityModelPartNames.ROOT), RenderLayer::getEntityTranslucent);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 23.5F, 0.0F));
        modelPartData2.addChild(
                EntityModelPartNames.HEAD,
                ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, -3.99F, 0.0F)
        );
        ModelPartData modelPartData3 = modelPartData2.addChild(
                EntityModelPartNames.BODY,
                ModelPartBuilder.create()
                        .uv(0, 10)
                        .cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
                        .uv(0, 16)
                        .cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 5.0F, 2.0F, new Dilation(-0.2F)),
                ModelTransform.pivot(0.0F, -4.0F, 0.0F)
        );
        modelPartData3.addChild(
                EntityModelPartNames.RIGHT_ARM,
                ModelPartBuilder.create().uv(23, 0).cuboid(-0.75F, -0.5F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(-0.01F)),
                ModelTransform.pivot(-1.75F, 0.5F, 0.0F)
        );
        modelPartData3.addChild(
                EntityModelPartNames.LEFT_ARM,
                ModelPartBuilder.create().uv(23, 6).cuboid(-0.25F, -0.5F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(-0.01F)),
                ModelTransform.pivot(1.75F, 0.5F, 0.0F)
        );
        modelPartData3.addChild(
                EntityModelPartNames.RIGHT_WING,
                ModelPartBuilder.create().uv(16, 14).cuboid(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-0.5F, 0.0F, 0.6F)
        );
        modelPartData3.addChild(
                EntityModelPartNames.LEFT_WING,
                ModelPartBuilder.create().uv(16, 14).cuboid(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.5F, 0.0F, 0.6F)
        );
        return TexturedModelData.of(modelData, 32, 32);
    }

    public void setAngles(FairyEntityRenderState fairyEntityRenderState) {
        super.setAngles(fairyEntityRenderState);
        float f = fairyEntityRenderState.limbAmplitudeMultiplier;
        float g = fairyEntityRenderState.limbFrequency;
        float h = fairyEntityRenderState.age * 20.0F * (float) (Math.PI / 180.0) + g;
        float i = MathHelper.cos(h) * (float) Math.PI * 0.15F + f;
        float j = fairyEntityRenderState.age * 9.0F * (float) (Math.PI / 180.0);
        float k = Math.min(f / 0.3F, 1.0F);
        float l = 1.0F - k;
        float m = fairyEntityRenderState.itemHoldAnimationTicks;
        if (fairyEntityRenderState.dancing) {
            float n = fairyEntityRenderState.age * 8.0F * (float) (Math.PI / 180.0) + f;
            float o = MathHelper.cos(n) * 16.0F * (float) (Math.PI / 180.0);
            float p = fairyEntityRenderState.spinningAnimationTicks;
            float q = MathHelper.cos(n) * 14.0F * (float) (Math.PI / 180.0);
            float r = MathHelper.cos(n) * 30.0F * (float) (Math.PI / 180.0);
            this.root.yaw = fairyEntityRenderState.spinning ? (float) (Math.PI * 4) * p : this.root.yaw;
            this.root.roll = o * (1.0F - p);
            this.head.yaw = r * (1.0F - p);
            this.head.roll = q * (1.0F - p);
        } else {
            this.head.pitch = fairyEntityRenderState.pitch * (float) (Math.PI / 180.0);
            this.head.yaw = fairyEntityRenderState.yawDegrees * (float) (Math.PI / 180.0);
        }

        this.rightWing.pitch = 0.43633232F * (1.0F - k);
        this.rightWing.yaw = (float) (-Math.PI / 4) + i;
        this.leftWing.pitch = 0.43633232F * (1.0F - k);
        this.leftWing.yaw = (float) (Math.PI / 4) - i;
        this.body.pitch = k * (float) (Math.PI / 4);
        float n = m * MathHelper.lerp(k, (float) (-Math.PI / 3), -1.134464F);
        this.root.pivotY = this.root.pivotY + (float)Math.cos((double)j) * 0.25F * l;
        this.rightArm.pitch = n;
        this.leftArm.pitch = n;
        float o = l * (1.0F - m);
        float p = 0.43633232F - MathHelper.cos(j + (float) (Math.PI * 3.0 / 2.0)) * (float) Math.PI * 0.075F * o;
        this.leftArm.roll = -p;
        this.rightArm.roll = p;
        this.rightArm.yaw = 0.27925268F * m;
        this.leftArm.yaw = -0.27925268F * m;
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        float f = 1.0F;
        float g = 3.0F;
        this.root.rotate(matrices);
        this.body.rotate(matrices);
        matrices.translate(0.0F, 0.0625F, 0.1875F);
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(this.rightArm.pitch));
        matrices.scale(0.7F, 0.7F, 0.7F);
        matrices.translate(0.0625F, 0.0F, 0.0F);
    }
}
