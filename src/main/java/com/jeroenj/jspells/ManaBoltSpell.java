package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import com.jeroenj.entity.JMagicEntities;
import com.jeroenj.entity.ManaBoltEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class ManaBoltSpell extends JSpell {
    public ManaBoltSpell() {
        super(JMagicJSpells.MANA_BOLT_SPELL, "Mana Bolt", "Shoot a single Mana Bolt.", 10, 10, JMagic.id("hud/icon/mana_bolt"));
    }

    @Override
    protected void performCast(ServerWorld world, Entity user) {
        ManaBoltEntity manaBolt = new ManaBoltEntity(JMagicEntities.MANA_BOLT, world);
        manaBolt.start(user.getPos(), Vec3d.ZERO);
        world.spawnEntity(manaBolt);
    }
}
