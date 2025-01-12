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
import com.jeroenj.particles.JMagicParticles;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
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
	}

	private void onServerTick(MinecraftServer server) {
		for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
			((ServerPlayerEntityAccess) player).jMagic$getSpellManager().tick(player);
		}
	}

	public static Identifier id(String path) { return Identifier.of(MOD_ID, path); }
	public static MutableText text(String type, String key) { return Text.translatable(type + ".jmagic." + key); }
}