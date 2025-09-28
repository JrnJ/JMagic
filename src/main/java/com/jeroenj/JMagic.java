package com.jeroenj;

import com.jeroenj.access.ServerPlayerEntityAccess;
import com.jeroenj.armor.JMagicArmorMaterials;
import com.jeroenj.attachments.JMagicAttachmentTypes;
import com.jeroenj.block.JMagicBlockEntityTypes;
import com.jeroenj.block.JMagicBlocks;
import com.jeroenj.effect.JMagicEffects;
import com.jeroenj.entity.JMagicEntities;
import com.jeroenj.item.GunItem;
import com.jeroenj.item.JMagicItems;
import com.jeroenj.jspells.JMagicJSpells;
import com.jeroenj.networking.JMagicPackets;
import com.jeroenj.networking.payload.*;
import com.jeroenj.networking.persistant.*;
import com.jeroenj.particles.JMagicParticles;
import com.jeroenj.potion.JMagicPotions;
import com.jeroenj.sound.JMagicSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		JMagicEffects.initialize();
		JMagicPotions.initialize();
		JMagicItems.initialize();
		JMagicBlocks.initialize();
		JMagicBlockEntityTypes.initialize();
		JMagicItemGroups.initialize();
		JMagicSounds.initialize();
		JMagicEntities.initialize();
		JMagicJSpells.initialize();
		JMagicAttachmentTypes.initialize();
		JMagicParticles.initialize();
		JMagicPackets.initialize();
		JMagicArmorMaterials.initialize();

		// Registering
		registerNetworking();
		registerOther();
	}

	private void registerNetworking() {
		// Networking
//		PayloadTypeRegistry.playS2C().register(JMagicTestPayload.ID, JMagicTestPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(JMagicDirtPayload.ID, JMagicDirtPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(InitialSyncPayload.ID, InitialSyncPayload.CODEC);

		PayloadTypeRegistry.playC2S().register(CastSpellPayload.ID, CastSpellPayload.PACKET_CODEC);
		PayloadTypeRegistry.playS2C().register(PlayerSpellsPayload.ID, PlayerSpellsPayload.PACKET_CODEC);
		PayloadTypeRegistry.playS2C().register(UsedSpellPayload.ID, UsedSpellPayload.PACKET_CODEC);

		ServerPlayNetworking.registerGlobalReceiver(CastSpellPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				((ServerPlayerEntityAccess) context.player()).jMagic$getSpellManager().cast(
						context.player().getServerWorld(), context.player(), payload.data().identifier());
			});
		});

		// Guns
		PayloadTypeRegistry.playC2S().register(GunShootPayload.ID, GunShootPayload.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(GunShootPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				if (context.player().getMainHandStack().getItem() instanceof GunItem gunItem) {
					gunItem.shoot(context.player());
				}
			});
		});

		// Debug
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			// TODO get from StateSaverAndLoader.getPlayerState
			server.execute(() -> {
				ServerPlayNetworking.send(handler.getPlayer(), new PlayerSpellsPayload(
						new PlayerSpellsData(List.of
								(
										JMagicJSpells.METEOR_SPELL,
										JMagicJSpells.TELEPORT_SPELL,
										JMagicJSpells.LEAP_SPELL,
										JMagicJSpells.MANA_BOLT_SPELL,
										JMagicJSpells.SHRINK_SPELL,
										JMagicJSpells.GROW_SPELL,
										JMagicJSpells.TOGGLE_SUN_GOD_SPELL,
										JMagicJSpells.SUN_GOD_GIANT_SPELL
								)
						)
				));
			});

			JMagicPlayerData playerState = StateSaverAndLoader.getPlayerState(handler.getPlayer());
			server.execute(() -> {
				ServerPlayNetworking.send(handler.getPlayer(), new JMagicDirtPayload(
						new JMagicDirtData(0, playerState.dirtBlocksBroken,
								List.of(
										new LEntry(1, 2.3),
										new LEntry(4, 5.6),
										new LEntry(7, 8.9)
								))));
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
							new JMagicDirtData(serverState.totalDirtBlocksBroken, playerState.dirtBlocksBroken,
									List.of(
											new LEntry(1, 2.3),
											new LEntry(4, 5.6),
											new LEntry(7, 8.9)
									))));
				});
			}
		});
	}

	private void registerOther() {
		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			if (!world.isClient() && hand == Hand.MAIN_HAND) {
				if (player.getStackInHand(hand).getItem() instanceof GunItem gunItem) {
					gunItem.shoot(player);
					return ActionResult.FAIL;
				}
			}

			return ActionResult.PASS;
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (!world.isClient() && hand == Hand.MAIN_HAND) {
				if (player.getStackInHand(hand).getItem() instanceof GunItem gunItem) {
					gunItem.shoot(player);
					return ActionResult.FAIL;
				}
			}

			return ActionResult.PASS;
		});
	}

	public static Identifier id(String path) { return Identifier.of(MOD_ID, path); }
	public static MutableText text(String type, String key) { return Text.translatable(type + ".jmagic." + key); }
}