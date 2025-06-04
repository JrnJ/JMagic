package com.jeroenj.entity.kitsune;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class KitsuneEntityModel extends EntityModel<KitsuneEntityRenderState> {
    private final ModelPart root;
    private final ModelPart paws;
    private final ModelPart front;
    private final ModelPart back;
    private final ModelPart rightLeg;
    private final ModelPart LeftLeg;
    private final ModelPart head;
    private final ModelPart ears;
    private final ModelPart tails;
    private final ModelPart leftTail;
    private final ModelPart middleTail;
    private final ModelPart rightTail;

    public KitsuneEntityModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.paws = root.getChild("paws");
        this.front = root.getChild("front");
        this.back = root.getChild("back");
        this.rightLeg = root.getChild("rightLeg");
        this.LeftLeg = root.getChild("LeftLeg");
        this.head = root.getChild("head");
        this.ears = root.getChild("ears");
        this.tails = root.getChild("tails");
        this.leftTail = root.getChild("leftTail");
        this.middleTail = root.getChild("middleTail");
        this.rightTail = root.getChild("rightTail");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -9.0F, -5.0F, 6.0F, 5.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData paws = modelPartData.addChild("paws", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData front = paws.addChild("front", ModelPartBuilder.create().uv(10, 48).cuboid(-3.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(18, 48).cuboid(1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, -4.0F));

        ModelPartData back = paws.addChild("back", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, -1.0F));

        ModelPartData rightLeg = back.addChild("rightLeg", ModelPartBuilder.create().uv(48, 0).cuboid(-1.0F, -2.0F, 2.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(30, 0).cuboid(-2.0F, -6.0F, 1.0F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 0.0F, 0.0F));

        ModelPartData LeftLeg = back.addChild("LeftLeg", ModelPartBuilder.create().uv(48, 6).cuboid(-1.0F, -2.0F, 2.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(28, 41).cuboid(-2.0F, -6.0F, 1.0F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(28, 28).cuboid(-4.0F, -7.0F, -1.0F, 8.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(30, 9).cuboid(-2.0F, -3.0F, -3.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 18.0F, -10.0F));

        ModelPartData ears = head.addChild("ears", ModelPartBuilder.create().uv(46, 47).cuboid(-5.0F, -10.0F, 1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 48).cuboid(2.0F, -10.0F, 1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData tails = modelPartData.addChild("tails", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F, 15.0F, 7.5F));

        ModelPartData leftTail = tails.addChild("leftTail", ModelPartBuilder.create().uv(0, 14).cuboid(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 9.0F, new Dilation(0.0F))
                .uv(0, 42).cuboid(-1.5F, -1.5F, 9.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.5F, 1.5F, -2.5F, 0.0F, 1.0908F, 0.0F));

        ModelPartData middleTail = tails.addChild("middleTail", ModelPartBuilder.create().uv(28, 14).cuboid(-2.5F, -1.0F, 0.0F, 5.0F, 5.0F, 9.0F, new Dilation(0.0F))
                .uv(12, 42).cuboid(-1.5F, 0.0F, 9.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 0.0F, -4.0F));

        ModelPartData rightTail = tails.addChild("rightTail", ModelPartBuilder.create().uv(0, 28).cuboid(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 9.0F, new Dilation(0.0F))
                .uv(46, 41).cuboid(-1.5F, -1.5F, 9.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 1.5F, -2.5F, 0.0F, -1.0908F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(KitsuneEntityRenderState state) {
        super.setAngles(state);
    }
}
