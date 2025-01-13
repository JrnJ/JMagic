package com.jeroenj.networking.persistant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record JMagicDirtData(int serverDirt, int clientDirt) {
    public static final Codec<JMagicDirtData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("serverDirt").forGetter(JMagicDirtData::serverDirt),
            Codec.INT.fieldOf("clientDirt").forGetter(JMagicDirtData::clientDirt)
    ).apply(instance, JMagicDirtData::new));

    public static PacketCodec<ByteBuf, JMagicDirtData> PACKET_CODEC = PacketCodecs.codec(CODEC);
}
