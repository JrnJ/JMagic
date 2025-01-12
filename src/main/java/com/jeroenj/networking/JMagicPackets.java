package com.jeroenj.networking;

import com.jeroenj.JMagic;
import net.minecraft.util.Identifier;

public final class JMagicPackets {
    public static final Identifier SELECT_SPELL_PACKET_ID = JMagic.id("jspell_select");
    public static final Identifier SPELL_INVENTORY_PACKET_ID = JMagic.id("jspell_inventory");

    public static void initialize() {

    }
}
