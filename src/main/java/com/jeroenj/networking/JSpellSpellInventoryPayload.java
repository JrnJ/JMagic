package com.jeroenj.networking;

import com.jeroenj.networking.codec.JSpellSpellInventory;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record JSpellSpellInventoryPayload(JSpellSpellInventory inventory) implements CustomPayload {
//    public static final CustomPayload.Id<JSpellSpellInventoryPayload> ID = new CustomPayload.Id<>(JMagicPackets.SPELL_INVENTORY_PACKET_ID);
//    public static final PacketCodec<RegistryByteBuf, JSpellSpellInventoryPayload> CODEC = PacketCodec.tuple(JSpellSpellInventory.PACKET_CODEC, JSpellSpellInventoryPayload::inventory, JSpellSpellInventoryPayload::new);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
//        return ID;
        return null;
    }
}
