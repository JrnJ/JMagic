package com.jeroenj;

import com.jeroenj.access.ClientPlayerEntityAccess;
import com.jeroenj.block.JMagicBlocks;
import com.jeroenj.entity.JMagicEntities;
import com.jeroenj.entity.JMagicModelLayers;
import com.jeroenj.entity.ManaBoltEntityRenderer;
import com.jeroenj.entity.MeteorEntityRenderer;
import com.jeroenj.entity.kitsune.KitsuneEntityRenderer;
import com.jeroenj.hud.SpellHud;
import com.jeroenj.hud.SpellSelectHud;
import com.jeroenj.hud.gun.GunHud;
import com.jeroenj.item.GunItem;
import com.jeroenj.item.InspectableItem;
import com.jeroenj.item.JMagicItems;
import com.jeroenj.item.ReloadableItem;
import com.jeroenj.jparticle.JParticleEffects;
import com.jeroenj.networking.JMagicPackets;
import com.jeroenj.networking.payload.*;
import com.jeroenj.networking.persistant.InitialSyncPayload;
import com.jeroenj.networking.persistant.JMagicDirtPayload;
import com.jeroenj.networking.persistant.JMagicPlayerData;
import com.jeroenj.networking.persistant.LEntry;
import com.jeroenj.particles.JMagicParticles;
import com.jeroenj.sound.JMagicSounds;
import com.jeroenj.util.JClientHelper;
import com.jeroenj.util.JHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class JMagicClient implements ClientModInitializer {
	public static MinecraftClient mc;

	// Keybindings
	public static KeyBinding selectSpellKeyBinding;

	public static KeyBinding quickCast1;
	public static KeyBinding quickCast2;
	public static KeyBinding quickCast3;
	public static KeyBinding quickCast4;
	public static KeyBinding inspectItem;
	public static KeyBinding reloadItem;

	public static JMagicPlayerData playerData = new JMagicPlayerData();

	@Override
	public void onInitializeClient() {
		mc = MinecraftClient.getInstance();

		JMagicModelLayers.initialize();
		JParticleEffects.initialize();
		// https://github.com/Nic4Las/Minecraft-Enderite-Mod/tree/Fabric-1.20
//		SpecialModelTypes.ID_MAPPER.put(JMagic.id("magic_wand"), new MagicWandItemRenderer());

		registerParticlesOnClient();
		registerKeybinds();
		registerEntityRenderers();
		registerHudCallbacks();
		registerNetworking();
		registerOther();
	}

	private void registerParticlesOnClient() {
		ParticleFactoryRegistry.getInstance().register(JMagicParticles.MAGIC_PARTICLE, EndRodParticle.Factory::new);
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.register(JMagicEntities.METEOR, MeteorEntityRenderer::new);
		EntityRendererRegistry.register(JMagicEntities.MANA_BOLT, ManaBoltEntityRenderer::new);
		EntityRendererRegistry.register(JMagicEntities.KITSUNE, KitsuneEntityRenderer::new);
	}

	private void registerHudCallbacks() {
		HudRenderCallback.EVENT.register(new SpellHud());
		HudRenderCallback.EVENT.register(new SpellSelectHud());
		HudRenderCallback.EVENT.register(new GunHud());
	}

	private void registerNetworking() {
		// Networking
//		ClientPlayNetworking.registerGlobalReceiver(JMagicTestPayload.ID, (payload, context) -> {
//			context.client().execute(() -> {
//				System.out.println(payload.testValue().iValue() + ":" + payload.testValue().dValue() + ":" + payload.testValue().sValue());
//			});
//		});

		ClientPlayNetworking.registerGlobalReceiver(JMagicDirtPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				context.client().player.sendMessage(Text.literal("Total dirt blocks broken: " + payload.value().serverDirt()), false);
				context.client().player.sendMessage(Text.literal("player specific dirt blocks broken: " + payload.value().clientDirt()), false);

				for (LEntry entry : payload.value().list())
				{
					context.client().player.sendMessage(Text.literal("List: " + entry.i() + " : " + entry.d()), false);
				}
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(InitialSyncPayload.ID, (payload, context) -> {
			playerData.dirtBlocksBroken = payload.value().clientDirt();

			context.client().execute(() -> {
				context.client().player.sendMessage(Text.literal("Initial specific dirt blocks broken: " + playerData.dirtBlocksBroken), false);
			});
		});

		// Sync Spells
		ClientPlayNetworking.registerGlobalReceiver(PlayerSpellsPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				((ClientPlayerEntityAccess) context.client().player).jMagic$getClientSpellManager().setSpells(payload.data().spells());
			});
		});
	}

	public static boolean cancelSwing = false;
	private void registerOther() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null || client.world == null) return;

			KeyBinding attackKey = client.options.attackKey;
			boolean isPressed = attackKey.isPressed();

			if (isPressed) {
				HitResult hit = client.crosshairTarget;
				if (hit == null || hit.getType() == HitResult.Type.MISS) {
					if (client.player.getMainHandStack().getItem() instanceof GunItem gun) {
						ClientPlayNetworking.send(new GunShootPayload(new GunShootData(JMagic.id("test"))));
//						client.player.handSwinging = false;
						cancelSwing = true;
//						client.player.getItemCooldownManager().set(client.player.getMainHandStack(), 0);
					}
				}
			}
		});

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), JMagicBlocks.GLUTINOUS_RICE_CROP_BLOCK);
	}

	private static boolean isSelectSpellKeyPressed = false;
	private void registerKeybinds() {
				selectSpellKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.jmagic.select_ability",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
				"category.jmagic.jmagic"
		));

		quickCast1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.jmagic.quick_cast_1",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z,
				"category.jmagic.jmagic"
		));

		quickCast2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.jmagic.quick_cast_2",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X,
				"category.jmagic.jmagic"
		));

		quickCast3 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.jmagic.quick_cast_3",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C,
				"category.jmagic.jmagic"
		));

		quickCast4 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.jmagic.quick_cast_4",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V,
				"category.jmagic.jmagic"
		));

		inspectItem = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.jmagic.inspect_item",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y,
				"category.jmagic.jmagic"
		));

		reloadItem = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.jmagic.reload_item",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
				"category.jmagic.jmagic"
		));

		//
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			{
				//
				{
					boolean isKeyPressed = selectSpellKeyBinding.isPressed();

					if (mc.player != null) {
						if (mc.player.getStackInHand(Hand.MAIN_HAND).isOf(JMagicItems.MAGIC_WAND)) {
							if (isKeyPressed && !isSelectSpellKeyPressed) {
								SpellSelectHud.show();
							}

							if (isSelectSpellKeyPressed && !isKeyPressed) {
								((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().selectSpell(SpellSelectHud.hide());
							}

							isSelectSpellKeyPressed = isKeyPressed;
						} else if (isKeyPressed && mc.player.getMainHandStack().getItem() instanceof ReloadableItem reloadableItem) {
							reloadableItem.reload(mc.player);
						}
					}
				}

				// Quick Cast
				while (quickCast1.wasPressed() && canCastSpell()) {
					((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().castSpell(0);
				}

				while (quickCast2.wasPressed() && canCastSpell()) {
					((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().castSpell(1);
				}

				while (quickCast3.wasPressed() && canCastSpell()) {
					((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().castSpell(2);
				}

				while (quickCast4.wasPressed() && canCastSpell()) {
					((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().castSpell(3);
				}

				// Other
				while (inspectItem.wasPressed()) {
					if (mc.player.getMainHandStack().getItem() instanceof InspectableItem inspectableItem) {
						inspectableItem.clientInspect(mc.player);
					}
				}
			}
		});
	}

	private static boolean canCastSpell() {
		return mc.player != null && mc.player.getStackInHand(Hand.MAIN_HAND).isOf(JMagicItems.MAGIC_WAND);
	}
}