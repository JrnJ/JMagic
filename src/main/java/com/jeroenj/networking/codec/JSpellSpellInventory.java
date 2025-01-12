package com.jeroenj.networking.codec;

import com.jeroenj.jspells.JSpell;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

import java.util.List;

public record JSpellSpellInventory(List<JSpell> inventory) {
//    public static final Codec<JSpellSpellInventory> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//            JSpell.CODEC.listOf().fieldOf("spells")
//    ))
//    public static final PacketCodec<ByteBuf, JSpellSpellInventory> PACKET_CODEC;
//
//    private final List<JSpell> spells;
//
//    public JSpellSpellInventory(List<JSpell> spells) {
//        this.spells = spells;
//        Codec<List<JSpell>> listCodec = JSpellSpellInventory.CODEC.listOf();
//    }
//
//    public List<JSpell> getSpells() { return spells; }
}
