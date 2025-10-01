package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import com.jeroenj.jspells.SunGod.DawnRocketSpell;
import com.jeroenj.jspells.SunGod.SunGodAirRun;
import com.jeroenj.jspells.SunGod.SunGodGiantSpell;
import com.jeroenj.jspells.SunGod.ToggleSunGodSpell;
import net.minecraft.util.Identifier;

public final class JMagicJSpells {
    public static final Identifier METEOR_SPELL = JMagic.id("meteor");
    public static final Identifier TELEPORT_SPELL = JMagic.id("teleport");
    public static final Identifier LEAP_SPELL = JMagic.id("leap");
    public static final Identifier MANA_BOLT_SPELL = JMagic.id("mana_bolt");
    public static final Identifier SHRINK_SPELL = JMagic.id("shrink");
    public static final Identifier GROW_SPELL = JMagic.id("grow");

    public static final Identifier TOGGLE_SUN_GOD_SPELL = JMagic.id("toggle_sun_god");
    public static final Identifier SUN_GOD_GIANT_SPELL = JMagic.id("sun_god_giant");
    public static final Identifier DAWN_ROCKET_SPELL = JMagic.id("dawn_rocket");
    public static final Identifier SUN_GOD_AIR_RUN = JMagic.id("sun_god_air_run");

    public static void initialize() {
        JSpellRegistry.register(METEOR_SPELL, MeteorSpell::new);
        JSpellRegistry.register(TELEPORT_SPELL, TeleportSpell::new);
        JSpellRegistry.register(LEAP_SPELL, LeapSpell::new);
        JSpellRegistry.register(MANA_BOLT_SPELL, ManaBoltSpell::new);
        JSpellRegistry.register(SHRINK_SPELL, ShrinkSpell::new);
        JSpellRegistry.register(GROW_SPELL, GrowSpell::new);

        JSpellRegistry.register(TOGGLE_SUN_GOD_SPELL, ToggleSunGodSpell::new);
        JSpellRegistry.register(SUN_GOD_GIANT_SPELL, SunGodGiantSpell::new);
        JSpellRegistry.register(DAWN_ROCKET_SPELL, DawnRocketSpell::new);
        JSpellRegistry.register(SUN_GOD_AIR_RUN, SunGodAirRun::new);
    }
}
