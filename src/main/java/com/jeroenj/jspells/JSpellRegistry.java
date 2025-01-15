package com.jeroenj.jspells;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class JSpellRegistry {
    private static final Map<Identifier, Supplier<JSpell>> SPELL_FACTORY = new HashMap<>();

    public static void register(Identifier identifier, Supplier<JSpell> spell) {
        SPELL_FACTORY.put(identifier, spell);
    }

    public static JSpell getSpell(Identifier identifier) {
        return SPELL_FACTORY.get(identifier).get();
    }
}
