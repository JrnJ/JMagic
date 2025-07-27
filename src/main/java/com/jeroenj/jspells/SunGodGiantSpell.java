package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class SunGodGiantSpell extends JSpell {
    public boolean active = false;

    public SunGodGiantSpell() {
        super(JMagicJSpells.SUN_GOD_GIANT_SPELL, "Giant Form", "Become a Giant", 50, 20, JMagic.id("hud/icon/sun_god_giant"));
    }

    @Override
    protected void performCast(ServerWorld world, ServerPlayerEntity user) {
        // /attribute Player43 minecraft:block_interaction_range base set 8.5
        active = !active;
        if (active) {
            enable(user);
        } else {
            disable(user);
        }
    }

    private void enable(ServerPlayerEntity user) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, -1, 2, false, false, false));

        EntityAttributeInstance scaleAttribute = user.getAttributeInstance(EntityAttributes.SCALE);
        if (scaleAttribute != null) scaleAttribute.setBaseValue(4.0f);

        EntityAttributeInstance blockInteractionAttribute = user.getAttributeInstance(EntityAttributes.BLOCK_INTERACTION_RANGE);
        if (blockInteractionAttribute != null) blockInteractionAttribute.setBaseValue(8.5f);
        EntityAttributeInstance entityInteractionAttribute = user.getAttributeInstance(EntityAttributes.ENTITY_INTERACTION_RANGE);
        if (entityInteractionAttribute != null) entityInteractionAttribute.setBaseValue(8.5f);
    }

    private void disable(ServerPlayerEntity user) {
        user.removeStatusEffect(StatusEffects.RESISTANCE);
        user.getAttributes().resetToBaseValue(EntityAttributes.SCALE);
        user.getAttributes().resetToBaseValue(EntityAttributes.BLOCK_INTERACTION_RANGE);
        user.getAttributes().resetToBaseValue(EntityAttributes.ENTITY_INTERACTION_RANGE);
    }
}
