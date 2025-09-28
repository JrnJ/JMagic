package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import com.jeroenj.entity.JMagicEntities;
import com.jeroenj.entity.ManaBoltEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class ManaBoltSpell extends JSpell {
    public ManaBoltSpell() {
        super(JMagicJSpells.MANA_BOLT_SPELL, "Mana Bolt", "Shoot a single Mana Bolt.", 10, 10, JMagic.id("hud/icon/mana_bolt"));
    }

    @Override
    protected void serverCast(ServerWorld world, ServerPlayerEntity user) {
        ManaBoltEntity manaBolt = new ManaBoltEntity(JMagicEntities.MANA_BOLT, world);
        manaBolt.start(user.getPos(), new Vec3d(1.0, 0.0, 0.0), user.getYaw(), user.getPitch());
        world.spawnEntity(manaBolt);
    }
}
