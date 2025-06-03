package com.jeroenj.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class KitsuneFlamesEffect extends StatusEffect {
    protected KitsuneFlamesEffect() {
        super(StatusEffectCategory.HARMFUL, 0x0000ec);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // 4x a second (20 ticks per second)
        return duration % 10 == 0;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        entity.damage(world, entity.getDamageSources().magic(), 1.0f);

        return super.applyUpdateEffect(world, entity, amplifier);
    }
}
