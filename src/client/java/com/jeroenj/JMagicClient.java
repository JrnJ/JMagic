package com.jeroenj;

import com.jeroenj.access.ClientPlayerEntityAccess;
import com.jeroenj.entity.JMagicEntities;
import com.jeroenj.entity.JMagicModelLayers;
import com.jeroenj.entity.ManaBoltEntityRenderer;
import com.jeroenj.entity.MeteorEntityRenderer;
import com.jeroenj.entity.kitsune.KitsuneEntityRenderer;
import com.jeroenj.hud.SpellHud;
import com.jeroenj.hud.SpellSelectHud;
import com.jeroenj.item.JMagicItems;
import com.jeroenj.networking.payload.PlayerSpellsPayload;
import com.jeroenj.networking.persistant.InitialSyncPayload;
import com.jeroenj.networking.persistant.JMagicDirtPayload;
import com.jeroenj.networking.persistant.JMagicPlayerData;
import com.jeroenj.networking.persistant.LEntry;
import com.jeroenj.particles.JMagicParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
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

	public static JMagicPlayerData playerData = new JMagicPlayerData();

	@Override
	public void onInitializeClient() {
		mc = MinecraftClient.getInstance();

		JMagicModelLayers.initialize();
		// https://github.com/Nic4Las/Minecraft-Enderite-Mod/tree/Fabric-1.20
//		SpecialModelTypes.ID_MAPPER.put(JMagic.id("magic_wand"), new MagicWandItemRenderer());

		registerParticlesOnClient();
		registerKeybinds();
		registerEntityRenderers();

		//
		HudRenderCallback.EVENT.register(new SpellHud());
		HudRenderCallback.EVENT.register(new SpellSelectHud());

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

	private void registerParticlesOnClient() {
		ParticleFactoryRegistry.getInstance().register(JMagicParticles.MAGIC_PARTICLE, EndRodParticle.Factory::new);
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.register(JMagicEntities.METEOR, MeteorEntityRenderer::new);
		EntityRendererRegistry.register(JMagicEntities.MANA_BOLT, ManaBoltEntityRenderer::new);
		EntityRendererRegistry.register(JMagicEntities.KITSUNE, KitsuneEntityRenderer::new);
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

		//
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			{
				if (mc.player != null) {
					if (!mc.player.getStackInHand(Hand.MAIN_HAND).isOf(JMagicItems.MAGIC_WAND)) {
						return;
					}
					boolean isKeyPressed = selectSpellKeyBinding.isPressed();

					if (!isSelectSpellKeyPressed && isKeyPressed) {
						SpellSelectHud.show();
					}

					if (isSelectSpellKeyPressed && !isKeyPressed) {
						((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().selectSpell(SpellSelectHud.hide());
					}

					isSelectSpellKeyPressed = isKeyPressed;
				}

				while (quickCast1.wasPressed()) {
					((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().castSpell(0);
				}

				while (quickCast2.wasPressed()) {
					((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().castSpell(1);
				}

				while (quickCast3.wasPressed()) {
					((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().castSpell(2);
				}

				while (quickCast4.wasPressed()) {
					((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().castSpell(3);
				}
			}
		});
	}
}