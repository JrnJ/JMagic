package com.jeroenj.jspells.SunGod;

import com.jeroenj.JMagic;
import com.jeroenj.jspells.JMagicJSpells;
import com.jeroenj.jspells.JSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class DawnRocketSpell extends JSpell {
    public DawnRocketSpell() {
        super(JMagicJSpells.DAWN_ROCKET_SPELL, "Dawn Rocket", "Dawn Rocket.", 10, 10, JMagic.id("hud/icon/dawn_rocket"));
    }

    @Override
    protected void serverCast(ServerWorld world, ServerPlayerEntity user) {
        System.out.println("DawnRocketSpell SERVER!");
    }

    @Override
    protected void clientCast(World world, PlayerEntity user) {
        System.out.println("DawnRocketSpell CLIENT!");
    }
}
