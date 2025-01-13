package com.jeroenj;

import com.jeroenj.access.ServerPlayerEntityAccess;
import com.jeroenj.attachments.JMagicAttachmentTypes;
import com.jeroenj.block.JMagicBlockEntityTypes;
import com.jeroenj.block.JMagicBlocks;
import com.jeroenj.entity.JMagicEntities;
import com.jeroenj.item.JMagicItems;
import com.jeroenj.jspells.JMagicJSpells;
import com.jeroenj.jspells.JSpellManager;
import com.jeroenj.jspells.JSpellRegistry;
import com.jeroenj.networking.JMagicPackets;
import com.jeroenj.networking.JMagicTestPayload;
import com.jeroenj.networking.codec.JMagicTestRecord;
import com.jeroenj.networking.persistant.*;
import com.jeroenj.particles.JMagicParticles;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class JMagic implements ModInitializer {
	public static final String MOD_ID = "jmagic";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private int totalDirtBlocksBroken = 0;

	@Override
	public void onInitialize() {
		JMagicItems.initialize();
		JMagicBlocks.initialize();
		JMagicBlockEntityTypes.initialize();
		JMagicItemGroups.initialize();
		JMagicEntities.initialize();
		JMagicJSpells.initialize();
		JMagicAttachmentTypes.initialize();
		JMagicParticles.initialize();
		JMagicPackets.initialize();

		ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);

//		PayloadTypeRegistry.playS2C().register(JMagicTestPayload.ID, JMagicTestPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(JMagicDirtPayload.ID, JMagicDirtPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(InitialSyncPayload.ID, InitialSyncPayload.CODEC);

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			JMagicPlayerData playerState = StateSaverAndLoader.getPlayerState(handler.getPlayer());
			server.execute(() -> {
				ServerPlayNetworking.send(handler.getPlayer(), new JMagicDirtPayload(
						new JMagicDirtData(0, playerState.dirtBlocksBroken)));
			});
		});

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			if (state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.DIRT) {
				StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());
				// Increment the amount of dirt blocks that have been broken
				serverState.totalDirtBlocksBroken += 1;

				JMagicPlayerData playerState = StateSaverAndLoader.getPlayerState(player);
				playerState.dirtBlocksBroken += 1;

				// Send a packet to the client
				MinecraftServer server = world.getServer();

				ServerPlayerEntity playerEntity = server.getPlayerManager().getPlayer(player.getUuid());
				server.execute(() -> {
					ServerPlayNetworking.send(playerEntity, new JMagicDirtPayload(
							new JMagicDirtData(serverState.totalDirtBlocksBroken, playerState.dirtBlocksBroken)));
				});
			}
		});
	}

	int tick = 0;

	private void onServerTick(MinecraftServer server) {
		tick++;

		for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
			((ServerPlayerEntityAccess) player).jMagic$getSpellManager().tick(player);

//			if (tick > 120) {
//				tick = 0;
//				ServerPlayNetworking.send(player, new JMagicTestPayload(new JMagicTestRecord(1, 2.3, "Banana")));
//			}
		}
	}

	public static Identifier id(String path) { return Identifier.of(MOD_ID, path); }
	public static MutableText text(String type, String key) { return Text.translatable(type + ".jmagic." + key); }
}