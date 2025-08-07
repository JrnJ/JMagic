package com.jeroenj.jparticle;

import com.jeroenj.JMagic;
import net.minecraft.util.Identifier;

public class JParticleEffects {

    public static Identifier GROUND_SLAM_PARTICLE_EFFECT = JMagic.id("ground_slam");

    public static void initialize() {
        JParticleEffectsRegistry.register(GROUND_SLAM_PARTICLE_EFFECT, GroundSlamParticleEffect::new);
    }
}
