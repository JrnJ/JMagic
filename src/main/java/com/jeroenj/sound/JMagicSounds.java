package com.jeroenj.sound;

import com.jeroenj.JMagic;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class JMagicSounds {
    public static final SoundEvent CARTOON_BOING = registerSoundEvent(JMagic.id("cartoon_boing"));
    public static final SoundEvent GROUND_IMPACT = registerSoundEvent(JMagic.id("ground_impact"));

    // Evori
    public static final SoundEvent EVORI_INSPECT = registerSoundEvent(JMagic.id("evori_inspect"));
    public static final SoundEvent EVORI_RELOAD = registerSoundEvent(JMagic.id("evori_reload"));
    public static final SoundEvent EVORI_SHOOT = registerSoundEvent(JMagic.id("evori_shoot"));
    public static final SoundEvent EVORI_SELECT = registerSoundEvent(JMagic.id("evori_select"));


    public static SoundEvent registerSoundEvent(Identifier identifier) {
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void initialize() { }
}
