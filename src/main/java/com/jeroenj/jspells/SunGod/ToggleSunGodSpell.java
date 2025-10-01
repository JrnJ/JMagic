package com.jeroenj.jspells.SunGod;

import com.jeroenj.JMagic;
import com.jeroenj.jspells.JMagicJSpells;
import com.jeroenj.jspells.JSpell;
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
    protected void serverCast(ServerWorld world, ServerPlayerEntity user) {
        active = !active;
        if (active) {
            enable(user);
        } else {
            disable(user);
        }
    }

    private void enable(ServerPlayerEntity user) {
        EntityAttributeInstance movementSpeedAttribute = user.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        if (movementSpeedAttribute != null) {
            movementSpeedAttribute.setBaseValue(0.18f);
        }
        EntityAttributeInstance jumpStrengthAttribute = user.getAttributeInstance(EntityAttributes.JUMP_STRENGTH);
        if (jumpStrengthAttribute != null) {
            jumpStrengthAttribute.setBaseValue(0.73f);
        }
        EntityAttributeInstance stepHeightAttribute = user.getAttributeInstance(EntityAttributes.STEP_HEIGHT);
        if (stepHeightAttribute != null) {
            stepHeightAttribute.setBaseValue(1.1f);
        }
    }

    private void disable(ServerPlayerEntity user) {
        user.getAttributes().resetToBaseValue(EntityAttributes.MOVEMENT_SPEED);
        user.getAttributes().resetToBaseValue(EntityAttributes.JUMP_STRENGTH);
        user.getAttributes().resetToBaseValue(EntityAttributes.STEP_HEIGHT);
    }
}
