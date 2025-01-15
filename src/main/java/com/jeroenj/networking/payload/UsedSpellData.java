package com.jeroenj.networking.payload;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

public record UsedSpellData(Identifier identifier, int atTick) {
    public static final Codec<UsedSpellData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("identifier").forGetter(UsedSpellData::identifier),
            Codec.INT.fieldOf("atTick").forGetter(UsedSpellData::atTick)
    ).apply(instance, UsedSpellData::new));

    public static PacketCodec<ByteBuf, UsedSpellData> PACKET_CODEC = PacketCodecs.codec(CODEC);
}
