package com.jeroenj.networking.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record JMagicTestRecord(int iValue, double dValue, String sValue) {
    public static final Codec<JMagicTestRecord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("iValue").forGetter(JMagicTestRecord::iValue),
            Codec.DOUBLE.fieldOf("dValue").forGetter(JMagicTestRecord::dValue),
            Codec.STRING.fieldOf("sValue").forGetter(JMagicTestRecord::sValue)
    ).apply(instance, JMagicTestRecord::new));

    public static PacketCodec<ByteBuf, JMagicTestRecord> PACKET_CODEC = PacketCodecs.codec(CODEC);
}
