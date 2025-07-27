// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.example.mod;
   
public class sun_god_aura extends EntityModel<Entity> {
	private final ModelPart Player;
	private final ModelPart left;
	private final ModelPart Right;
	private final ModelPart bb_main;
	public sun_god_aura(ModelPart root) {
		this.Player = root.getChild("Player");
		this.left = root.getChild("left");
		this.Right = root.getChild("Right");
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Player = modelPartData.addChild("Player", ModelPartBuilder.create().uv(-5, -2).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F))
		.uv(-9, -2).cuboid(-8.0F, -24.0F, -2.0F, 16.0F, 12.0F, 4.0F, new Dilation(0.0F))
		.uv(-9, -6).cuboid(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData left = modelPartData.addChild("left", ModelPartBuilder.create(), ModelTransform.pivot(6.0F, -2.7862F, 3.6532F));

		ModelPartData top_r1 = left.addChild("top_r1", ModelPartBuilder.create().uv(16, 14).cuboid(-3.0F, -22.253F, -1.9164F, 6.0F, 10.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 7.7862F, -9.6532F, -1.0472F, 0.0F, 0.0F));

		ModelPartData top_middle_r1 = left.addChild("top_middle_r1", ModelPartBuilder.create().uv(12, 24).cuboid(-3.0F, -11.7341F, -4.015F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 7.7862F, -9.6532F, -1.2217F, 0.0F, 0.0F));

		ModelPartData bottom_middle_r1 = left.addChild("bottom_middle_r1", ModelPartBuilder.create().uv(24, 0).cuboid(-3.0F, -7.0F, 0.0F, 6.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 7.7862F, -9.6532F, -0.6109F, 0.0F, 0.0F));

		ModelPartData bottom_r1 = left.addChild("bottom_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 14.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 7.7862F, -9.6532F, 0.0873F, 0.0F, 0.0F));

		ModelPartData Right = modelPartData.addChild("Right", ModelPartBuilder.create(), ModelTransform.pivot(-6.0F, -2.7862F, 3.6532F));

		ModelPartData top_r2 = Right.addChild("top_r2", ModelPartBuilder.create().uv(0, 20).cuboid(-3.0F, -22.253F, -1.9164F, 6.0F, 10.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 7.7862F, -9.6532F, -1.0472F, 0.0F, 0.0F));

		ModelPartData top_middle_r2 = Right.addChild("top_middle_r2", ModelPartBuilder.create().uv(24, 24).cuboid(-3.0F, -11.7341F, -4.015F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 7.7862F, -9.6532F, -1.2217F, 0.0F, 0.0F));

		ModelPartData bottom_middle_r2 = Right.addChild("bottom_middle_r2", ModelPartBuilder.create().uv(24, 7).cuboid(-3.0F, -7.0F, 0.0F, 6.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 7.7862F, -9.6532F, -0.6109F, 0.0F, 0.0F));

		ModelPartData bottom_r2 = Right.addChild("bottom_r2", ModelPartBuilder.create().uv(12, 0).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 14.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 7.7862F, -9.6532F, 0.0873F, 0.0F, 0.0F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData behind_head_r1 = bb_main.addChild("behind_head_r1", ModelPartBuilder.create().uv(0, 14).cuboid(-8.0F, -4.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -29.7862F, 8.8493F, -1.0472F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Player.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		left.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		Right.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}