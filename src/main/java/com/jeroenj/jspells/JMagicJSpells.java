package com.jeroenj.jspells;

import com.jeroenj.JMagic;

public final class JMagicJSpells {
    public static void initialize() {
        JSpellRegistry.register(JMagic.id("meteor"), new MeteorSpell());
        JSpellRegistry.register(JMagic.id("teleport"), new TeleportSpell());
        JSpellRegistry.register(JMagic.id("leap"), new LeapSpell());
    }
}
