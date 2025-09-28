package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class ToggleSunGodSpell extends JSpell {
    public static boolean active = false;

    public ToggleSunGodSpell() {
        super(JMagicJSpells.TOGGLE_SUN_GOD_SPELL, "Sun God", "Become the Sun God", 50, 60, JMagic.id("hud/icon/toggle_sun_god"));
    }

    @Override
    protected void performCast(ServerWorld world, ServerPlayerEntity user) {
        active = !active;
        if (active) {
            enable(user);
        } else {
            disable(user);
        }
    }

    private void enable(ServerPlayerEntity user) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, -1, 2, false, false, false));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 3, false, false, false));
        EntityAttributeInstance stepHeightAttribute = user.getAttributeInstance(EntityAttributes.STEP_HEIGHT);
        if (stepHeightAttribute != null) {
            stepHeightAttribute.setBaseValue(1.1f);
        }
    }

    private void disable(ServerPlayerEntity user) {
        user.removeStatusEffect(StatusEffects.JUMP_BOOST);
        user.removeStatusEffect(StatusEffects.SPEED);
        user.getAttributes().resetToBaseValue(EntityAttributes.STEP_HEIGHT);
    }
}
