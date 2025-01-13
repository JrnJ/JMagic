package com.jeroenj.networking;

import com.jeroenj.JMagic;
import net.minecraft.util.Identifier;

public final class JMagicPackets {
    public static final Identifier TEST_PACKET_ID = JMagic.id("test");
    public static final Identifier DIRT_BROKEN_ID = JMagic.id("dirt_broken");
    public static final Identifier INITIAL_SYNC_ID = JMagic.id("initial_sync");

    public static final Identifier SELECT_SPELL_PACKET_ID = JMagic.id("jspell_select");
    public static final Identifier SPELL_INVENTORY_PACKET_ID = JMagic.id("jspell_inventory");

    public static void initialize() {

    }
}
