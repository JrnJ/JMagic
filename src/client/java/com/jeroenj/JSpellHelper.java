package com.jeroenj;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;

import static com.jeroenj.JMagicClient.mc;

public final class JSpellHelper {
    public static void drawCooldownProgress(DrawContext context, int x, int y, int sizeX, int sizeY, int maxCooldown, int cooldown) {
        if (cooldown == 0) return;
        int progress = Math.max(1, (int)((float)sizeY / maxCooldown * cooldown));
        context.fill(RenderLayer.getGui(), x, y + sizeY - progress, x + sizeX, y + sizeY, 200, 0x70FFFFFF);
    }

    public static void drawOutlinedText(DrawContext context, String text, int x, int y) {
        context.drawText(mc.textRenderer, text, x + 1, y, 0, false);
        context.drawText(mc.textRenderer, text, x - 1, y, 0, false);
        context.drawText(mc.textRenderer, text, x, y + 1, 0, false);
        context.drawText(mc.textRenderer, text, x, y - 1, 0, false);
        context.drawText(mc.textRenderer, text, x, y, 0xFF30ACCC, false);
    }
}
