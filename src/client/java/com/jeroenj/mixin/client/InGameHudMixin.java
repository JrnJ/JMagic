package com.jeroenj.mixin.client;

import com.jeroenj.hud.SpellHud;
import com.jeroenj.hud.SpellSelectHud;
import com.jeroenj.item.JMagicItems;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.jeroenj.JMagicClient.mc;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(at = @At("HEAD"), method = "renderCrosshair", cancellable = true)
    private void renderCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (SpellSelectHud.isShowing()) {
            ci.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "shouldRenderExperience", cancellable = true)
    private void shouldRenderExperience(CallbackInfoReturnable<Boolean> cir) {
        if (mc.player.getStackInHand(Hand.MAIN_HAND).isOf(JMagicItems.MAGIC_WAND)) {
            cir.setReturnValue(false);
        }
    }
}
