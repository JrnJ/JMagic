package com.jeroenj.model;

import com.jeroenj.JMagicClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class DawnRocketEntityModel<T extends PlayerEntityRenderState> extends EntityModel<T> {

    private static final String LEFT_ARM_PARENT = "leftArmParent";
    private static final String LEFT_ARM = "leftArm";

    private static final String RIGHT_ARM_PARENT = "rightArmParent";
    private static final String RIGHT_ARM = "rightArm";

    private final ModelPart leftArmParent;
    private final ModelPart leftArm;

    private final ModelPart rightArmParent;
    private final ModelPart rightArm;

    public DawnRocketEntityModel(ModelPart root) {
        super(root);

        this.leftArmParent = root.getChild(LEFT_ARM_PARENT);
        this.leftArm = leftArmParent.getChild(LEFT_ARM);

        this.rightArmParent = root.getChild(RIGHT_ARM_PARENT);
        this.rightArm = rightArmParent.getChild(RIGHT_ARM);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData leftArmParent = modelPartData.addChild(LEFT_ARM_PARENT, ModelPartBuilder.create(),
                ModelTransform.of(6.0f, 2.0f, 2.0f, 0.0f, 0.0f, 0.0f));

        leftArmParent.addChild(LEFT_ARM, ModelPartBuilder.create().uv(26, 0)
                        .cuboid(-2.0f, -2.0f, 0.0f, 4.0f, 4.0f, 1.0f),
                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)); // 180.0f * MathHelper.RADIANS_PER_DEGREE


        ModelPartData rightArmParent = modelPartData.addChild(RIGHT_ARM_PARENT, ModelPartBuilder.create(),
                ModelTransform.of(-6.0f, 2.0f, 2.0f, 0.0f, 0.0f, 0.0f));

        rightArmParent.addChild(RIGHT_ARM, ModelPartBuilder.create().uv(26, 0)
                        .cuboid(-2.0f, -2.0f, 0.0f, 4.0f, 4.0f, 1.0f),
                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T state) {
        super.setAngles(state);

        armTest(state, leftArm, leftArmParent);
        armTest(state, rightArm, rightArmParent);
    }

    public void armTest(T state, ModelPart arm, ModelPart armParent) {
        // Convert body rotation to radians
        // 1. World target position
        Vec3d target = new Vec3d(-226, 56, 174); // change this to your target

        // 2. Compute pivot world position (model pivot + entity position)
        double pivotWorldX = state.x + armParent.pivotX / 16.0;
        double pivotWorldY = state.y + armParent.pivotY / 16.0;
        double pivotWorldZ = state.z + armParent.pivotZ / 16.0;

        Vec3d pivotWorld = new Vec3d(pivotWorldX, pivotWorldY, pivotWorldZ);

        // 3. Direction vector to target
        Vec3d dir = target.subtract(pivotWorld);

        // 4. Rotate dir into body-local space
        float bodyYawRad = (float) Math.toRadians(state.bodyYaw); // entity yaw in radians
        double cos = Math.cos(-bodyYawRad);
        double sin = Math.sin(-bodyYawRad);

        double localX = dir.x * cos - dir.z * sin;
        double localZ = dir.x * sin + dir.z * cos;
        double localY = dir.y;

        Vec3d localDir = new Vec3d(localX, localY, localZ);

        // 5. Compute yaw and pitch for the arm parent
        float yaw = (float) Math.atan2(localDir.x, -localDir.z); // invert Z for Minecraft model
        float pitch = (float) Math.atan2(localDir.y, Math.sqrt(localDir.x * localDir.x + localDir.z * localDir.z));

        // 6. Apply rotations to parent
        armParent.yaw = yaw;
        armParent.pitch = pitch;

        // 7. Stretch arm mesh along Z axis
        arm.zScale = (float) localDir.length() * 16.0f; //  / state.baseScale kinda worked
    }
}
