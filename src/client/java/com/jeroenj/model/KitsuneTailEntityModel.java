package com.jeroenj.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;

@Environment(EnvType.CLIENT)
public class KitsuneTailEntityModel<T extends PlayerEntityRenderState> extends EntityModel<T> {

    // Part Names
    private static final String KITSUNE_TAIL_PART_0 = "kitsune_tail_part_0";
    private static final String KITSUNE_TAIL_PART_1 = "kitsune_tail_part_1";
    private static final String KITSUNE_TAIL_PART_2 = "kitsune_tail_part_2";
    private static final String KITSUNE_TAIL_PART_3 = "kitsune_tail_part_3";
    private static final String KITSUNE_TAIL_PART_4 = "kitsune_tail_part_4";
    private static final String KITSUNE_TAIL_PART_5 = "kitsune_tail_part_5";

    private final ModelPart root;
    private final ModelPart[] kitsuneTail;

    public KitsuneTailEntityModel(ModelPart root) {
        super(root);
        this.root = root;

        this.kitsuneTail = new ModelPart[6];
        this.kitsuneTail[0] = this.root.getChild(KITSUNE_TAIL_PART_0);
        this.kitsuneTail[1] = this.kitsuneTail[0].getChild(KITSUNE_TAIL_PART_1);
        this.kitsuneTail[2] = this.kitsuneTail[1].getChild(KITSUNE_TAIL_PART_2);
        this.kitsuneTail[3] = this.kitsuneTail[2].getChild(KITSUNE_TAIL_PART_3);
        this.kitsuneTail[4] = this.kitsuneTail[3].getChild(KITSUNE_TAIL_PART_4);
        this.kitsuneTail[5] = this.kitsuneTail[4].getChild(KITSUNE_TAIL_PART_5);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData part0 = modelPartData.addChild(KITSUNE_TAIL_PART_0, ModelPartBuilder.create()
                        .uv(26, 0)
                        .cuboid(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 4.0f), // size of X2*Y2*Z4
                ModelTransform.of(0.0f, 7.90875f, 1.5825f, -0.429351f, 0.0f, 0.0f) // -24.6f degrees
        );

        ModelPartData part1 = part0.addChild(KITSUNE_TAIL_PART_1, ModelPartBuilder.create()
                        .uv(26, 6)
                        .cuboid(-1.5f, -1.5f, 0.0f, 3.0f, 3.0f, 4.0f),
                ModelTransform.of(0.0f, 0.0f, 1.75f, -0.244346f, 0.0f, 0.0f) // -38.6f degrees -> -14.0f
        );

        ModelPartData part2 = part1.addChild(KITSUNE_TAIL_PART_2, ModelPartBuilder.create()
                        .uv(26, 13)
                        .cuboid(-2.0f, -2.0f, 0.0f, 4.0f, 4.0f, 4.0f),
                ModelTransform.of(0.0f, 0.0f, 2.235f, -0.174533f, 0.0f, 0.0f) // -48.6f degrees -> -10.0f
        );

        ModelPartData part3 = part2.addChild(KITSUNE_TAIL_PART_3, ModelPartBuilder.create()
                        .uv(26, 21)
                        .cuboid(-2.5f, -2.5f, 0.0f, 5.0f, 5.0f, 8.0f),
                ModelTransform.of(0.0f, 0.0f, 2.6f, -0.4642576f, 0.0f, 0.0f) // 22.0 degrees -> -26.6f
        );

        ModelPartData part4 = part3.addChild(KITSUNE_TAIL_PART_4, ModelPartBuilder.create()
                        .uv(26, 34)
                        .cuboid(-2.0f, -2.0f, 0.0f, 4.0f, 4.0f, 3.0f),
                ModelTransform.of(0.0f, 0.35f, 6.8125f, 0.2600541f, 0.0f, 0.0f) // 36.9 degrees -> 14.9f
        );

        part4.addChild(KITSUNE_TAIL_PART_5, ModelPartBuilder.create()
                        .uv(26, 41)
                        .cuboid(-1.5f, -1.5f, 0.0f, 3.0f, 3.0f, 2.0f),
                ModelTransform.of(0.0f, 0.3f, 2.25f, 0.3089233f, 0.0f, 0.0f) // 54.6 degrees -> 17.7f
        );

        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void setAngles(T state) {
        super.setAngles(state);
    }
}
