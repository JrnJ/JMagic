package com.jeroenj.entity.Fairy;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;

@Environment(EnvType.CLIENT)
public class FairyEntityRenderState extends ArmedEntityRenderState {
    public boolean dancing;
    public boolean spinning;
    public float spinningAnimationTicks;
    public float itemHoldAnimationTicks;
}
