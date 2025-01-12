package com.jeroenj.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.EntityRenderState;

@Environment(EnvType.CLIENT)
public class MeteorEntityRenderState extends EntityRenderState {

    public float pitch;
    public float yaw;

    public MeteorEntityRenderState() {

    }
}
