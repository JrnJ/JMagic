package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import net.minecraft.util.Identifier;

public final class JMagicJSpells {
    public static final Identifier METEOR_SPELL = JMagic.id("meteor");
    public static final Identifier TELEPORT_SPELL = JMagic.id("teleport");
    public static final Identifier LEAP_SPELL = JMagic.id("leap");

    public static void initialize() {
        JSpellRegistry.register(METEOR_SPELL, MeteorSpell::new);
        JSpellRegistry.register(TELEPORT_SPELL, TeleportSpell::new);
        JSpellRegistry.register(LEAP_SPELL, LeapSpell::new);
    }
}
