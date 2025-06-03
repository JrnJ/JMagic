package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class ShrinkSpell extends SizeChangeSpell {
    public ShrinkSpell() {
        super(JMagicJSpells.SHRINK_SPELL, "Shrink", "Shrink yourself.", 10, 10, JMagic.id("hud/icon/shrink"));
    }

    @Override
    protected void performCast(ServerWorld world, ServerPlayerEntity user) {
        if (user.isSneaking()) {
            decreaseSize(user, 1);
        } else {
            decreaseSize(user, 2);
        }
    }
}
