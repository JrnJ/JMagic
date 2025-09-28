package com.jeroenj.networking;

import com.jeroenj.JMagic;
import net.minecraft.util.Identifier;

public final class JMagicPackets {
    // Debug
    public static final Identifier TEST_PACKET_ID = JMagic.id("test");
    public static final Identifier DIRT_BROKEN_ID = JMagic.id("dirt_broken");
    public static final Identifier INITIAL_SYNC_ID = JMagic.id("initial_sync");

    public static final Identifier SELECT_SPELL_PACKET_ID = JMagic.id("jspell_select");

    // Final
    public static final Identifier SYNC_PLAYER_SPELLS_ID = JMagic.id("get_player_spells");
    public static final Identifier CAST_SPELL_ID = JMagic.id("cast_spell");
    public static final Identifier USED_SPELL_ID = JMagic.id("used_spell");
    public static final Identifier SPELL_CHANGED_ID = JMagic.id("spell_changed");

    // Gun
    public static final Identifier GUN_SHOOT_ID = JMagic.id("gun_shoot");

    public static void initialize() {

    }
}
