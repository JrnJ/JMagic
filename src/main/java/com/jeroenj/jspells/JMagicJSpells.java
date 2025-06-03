package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import net.minecraft.util.Identifier;

public final class JMagicJSpells {
    public static final Identifier METEOR_SPELL = JMagic.id("meteor");
    public static final Identifier TELEPORT_SPELL = JMagic.id("teleport");
    public static final Identifier LEAP_SPELL = JMagic.id("leap");
    public static final Identifier MANA_BOLT_SPELL = JMagic.id("mana_bolt");
    public static final Identifier SHRINK_SPELL = JMagic.id("shrink");
    public static final Identifier GROW_SPELL = JMagic.id("grow");

    public static void initialize() {
        JSpellRegistry.register(METEOR_SPELL, MeteorSpell::new);
        JSpellRegistry.register(TELEPORT_SPELL, TeleportSpell::new);
        JSpellRegistry.register(LEAP_SPELL, LeapSpell::new);
        JSpellRegistry.register(MANA_BOLT_SPELL, ManaBoltSpell::new);
        JSpellRegistry.register(SHRINK_SPELL, ShrinkSpell::new);
        JSpellRegistry.register(GROW_SPELL, GrowSpell::new);
    }
}
