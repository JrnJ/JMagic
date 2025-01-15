package com.jeroenj.networking.persistant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LEntry(int i, double d) {
    public static final Codec<LEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("i").forGetter(LEntry::i),
            Codec.DOUBLE.fieldOf("d").forGetter(LEntry::d)
    ).apply(instance, LEntry::new));
}
