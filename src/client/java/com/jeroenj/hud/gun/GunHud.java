package com.jeroenj.hud.gun;

import com.jeroenj.item.GunItem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;

import static com.jeroenj.JMagicClient.mc;

public class GunHud implements HudRenderCallback {
    private static boolean showHud = false;

    public static void show() {
        showHud = true;
    }

    public static void hide() {
        showHud = false;
    }

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter renderTickCounter) {
//        if (!showHud) return;
        if (mc.player.getMainHandStack().getItem() instanceof GunItem gunItem)
        {
            int originX = context.getScaledWindowWidth() / 2;
            int originY = context.getScaledWindowHeight() / 2;

            double screenCenterX = mc.getWindow().getWidth() * 0.5;
            double screenCenterY = mc.getWindow().getHeight() * 0.5;

            context.getMatrices().push();

//            context.drawTextWithShadow(mc.textRenderer, "10/30", 10, 10, 10, 10);
            context.drawTextWithShadow(mc.textRenderer,
                    Text.literal(gunItem.getAmmo() + " / " + gunItem.maxAmmo), originX + 100, context.getScaledWindowHeight() - 18, 0xFFFFFFFF);

            context.getMatrices().pop();
        }
    }
}
