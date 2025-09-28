package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class GrowSpell extends SizeChangeSpell {
    public GrowSpell() {
        super(JMagicJSpells.GROW_SPELL, "Grow", "Grow yourself.", 10, 10, JMagic.id("hud/icon/grow"));
    }

    @Override
    protected void serverCast(ServerWorld world, ServerPlayerEntity user) {
        if (user.isSneaking()) {
            increaseSize(user, 1);
        } else {
            increaseSize(user, 2);
        }
    }
}
