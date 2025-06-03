package com.jeroenj.effect;

import com.jeroenj.JMagic;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class JMagicEffects {

    public static final RegistryEntry<StatusEffect> KITSUNE_FLAMES =
            Registry.registerReference(Registries.STATUS_EFFECT, JMagic.id("kitsune_flames"), new KitsuneFlamesEffect());

    public static void initialize() { }
}
