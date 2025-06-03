package com.jeroenj.jspells;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public abstract class SizeChangeSpell extends JSpell {
    public final double MIN_SIZE = 1.0 / 16.0 * 4.0; // 0.25 blocks
    public final double MAX_SIZE = 1.0 / 16.0 * 64.0; // 4 blocks

    public SizeChangeSpell(Identifier id, String name, String description, int manaCost, int cooldown, Identifier slotTexture) {
        super(id, name, description, manaCost, cooldown, slotTexture);
    }

    @Override
    public String spellSelectInfo(PlayerEntity user) {
        return "Current size: " + getSize(user);
    }

    protected void increaseSize(ServerPlayerEntity user, int pixels) {
        EntityAttributeInstance scaleAttribute = user.getAttributeInstance(EntityAttributes.SCALE);
        if (scaleAttribute == null) {
            return;
        }

        double newSize = (1.0 / 16.0) * (((int)(16.0 * scaleAttribute.getBaseValue())) + pixels);
        if (newSize <= MIN_SIZE) newSize = MIN_SIZE;
        if (newSize >= MAX_SIZE) newSize = MAX_SIZE;
        scaleAttribute.setBaseValue(newSize);
        showInfoMessage("Size is now: " + newSize);
    }

    protected void decreaseSize(ServerPlayerEntity user, int pixels) {
        EntityAttributeInstance scaleAttribute = user.getAttributeInstance(EntityAttributes.SCALE);
        if (scaleAttribute == null) {
            return;
        }

        double newSize = (1.0 / 16.0) * (((int)(16.0 * scaleAttribute.getBaseValue())) - pixels);
        if (newSize <= MIN_SIZE) newSize = MIN_SIZE;
        if (newSize >= MAX_SIZE) newSize = MAX_SIZE;
        scaleAttribute.setBaseValue(newSize);
        showInfoMessage("Size is now: " + newSize);
    }

    private double getSize(PlayerEntity user) {
        EntityAttributeInstance scaleAttribute = user.getAttributeInstance(EntityAttributes.SCALE);
        if (scaleAttribute == null) {
            return 1.0;
        }

        return scaleAttribute.getBaseValue();
    }
}
