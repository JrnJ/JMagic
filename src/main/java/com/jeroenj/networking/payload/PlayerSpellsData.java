package com.jeroenj.networking.payload;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

import java.util.List;

public record PlayerSpellsData(List<Identifier> spells) {
    public static final Codec<PlayerSpellsData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.list(Identifier.CODEC).fieldOf("spells").forGetter(PlayerSpellsData::spells)
    ).apply(instance, PlayerSpellsData::new));

    public static PacketCodec<ByteBuf, PlayerSpellsData> PACKET_CODEC = PacketCodecs.codec(CODEC);
}
