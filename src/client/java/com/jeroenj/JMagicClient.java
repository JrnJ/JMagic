package com.jeroenj;

import com.jeroenj.entity.JMagicEntities;
import com.jeroenj.entity.JMagicModelLayers;
import com.jeroenj.entity.MeteorEntityRenderer;
import com.jeroenj.hud.SpellHud;
import com.jeroenj.hud.SpellSelectHud;
import com.jeroenj.particles.JMagicParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class JMagicClient implements ClientModInitializer {
	public static MinecraftClient mc;

	// Keybindings
	public static KeyBinding selectSpellKeyBinding;

	@Override
	public void onInitializeClient() {
		mc = MinecraftClient.getInstance();

		JMagicModelLayers.initialize();

		registerParticlesOnClient();
		registerKeybinds();
		registerEntityRenderers();

		//
		HudRenderCallback.EVENT.register(new SpellHud());
	}

	private void registerParticlesOnClient() {
		ParticleFactoryRegistry.getInstance().register(JMagicParticles.MAGIC_PARTICLE, EndRodParticle.Factory::new);
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.register(JMagicEntities.METEOR, MeteorEntityRenderer::new);
	}

	private static boolean isSelectSpellKeyPressed = false;
	private void registerKeybinds() {
		selectSpellKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.jmagic.select_ability",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
				"category.jmagic.jmagic"
		));

		//
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			{
				boolean isKeyPressed = selectSpellKeyBinding.isPressed();

				if (!isSelectSpellKeyPressed && isKeyPressed) {
					SpellSelectHud.show();
				}

				if (isSelectSpellKeyPressed && !isKeyPressed) {
					// Send Packet
					// TODO JMagic.spellManager.select(SpellSelectHud.hide());
					SpellSelectHud.hide();
				}

				isSelectSpellKeyPressed = isKeyPressed;
			}
		});
	}
}