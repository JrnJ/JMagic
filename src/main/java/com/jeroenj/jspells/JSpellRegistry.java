package com.jeroenj.jspells;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class JSpellRegistry {
    private static final Map<Identifier, JSpell> SPELLS = new HashMap<>();

    public static void register(Identifier identifier, JSpell spell) {
        SPELLS.put(identifier, spell);
    }

    public static JSpell getSpell(Identifier identifier) {
        return SPELLS.get(identifier);
    }
}
