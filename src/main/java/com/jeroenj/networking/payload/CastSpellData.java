package com.jeroenj.networking.payload;

import com.jeroenj.networking.persistant.JMagicDirtData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

public record CastSpellData(Identifier identifier) {
    public static final Codec<CastSpellData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("identifier").forGetter(CastSpellData::identifier)
    ).apply(instance, CastSpellData::new));
    public static PacketCodec<ByteBuf, CastSpellData> PACKET_CODEC = PacketCodecs.codec(CODEC);
}
