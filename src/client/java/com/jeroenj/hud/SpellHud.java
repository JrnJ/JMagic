package com.jeroenj.hud;

import com.jeroenj.JMagic;
import com.jeroenj.JSpellHelper;
import com.jeroenj.attachments.JMagicAttachmentTypes;
import com.jeroenj.item.JMagicItems;
import com.jeroenj.jspells.JMagicJSpells;
import com.jeroenj.jspells.JSpell;
import com.jeroenj.jspells.JSpellRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import static com.jeroenj.JMagicClient.mc;

public class SpellHud implements HudRenderCallback {
    private static final Identifier SPELL_LEFT_TEXTURE = Identifier.ofVanilla("hud/hotbar_offhand_left");
    private static final Identifier SPELL_RIGHT_TEXTURE = Identifier.ofVanilla("hud/hotbar_offhand_right");

    private static final Identifier MANA_BAR_BACKGROUND_TEXTURE = JMagic.id("hud/mana_bar_background");
    private static final Identifier MANA_BAR_PROGRESS_TEXTURE = JMagic.id("hud/mana_bar_progress");

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (mc.player.getStackInHand(Hand.MAIN_HAND).isOf(JMagicItems.MAGIC_WAND)) {
            int currentMana = mc.player.getAttachedOrCreate(JMagicAttachmentTypes.PLAYER_MANA).getCurrentMana();
            renderSelectedSpell(drawContext);
            renderManaBar(drawContext, currentMana);
            renderManaBarText(drawContext, currentMana);
        }
    }

    private void renderSelectedSpell(DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().translate(0.0, 0.0, 3000.0);
        int i = context.getScaledWindowWidth() / 2;
        int offhandSlotWidth = 29;

        // TODO: JSpell selectedSpell = JMagic.spellManager.getSelected();
        JSpell selectedSpell = JSpellRegistry.getSpell(JMagic.id("meteor"));
        if (mc.player.getMainArm() == Arm.LEFT) {
            int x = i - 91 - 29;
            int y = context.getScaledWindowHeight() - 23;
            context.drawGuiTexture(RenderLayer::getGuiTextured, SPELL_LEFT_TEXTURE, x, y, offhandSlotWidth, 24);
            context.drawGuiTexture(RenderLayer::getGuiTextured, selectedSpell.getSlotTexture(), x + 3, y + 4, 16, 16);
            JSpellHelper.drawCooldownProgress(context,x + 3, y + 4, 16, 16, selectedSpell.getCooldown(), selectedSpell.getCooldownTimer());
        } else {
            int x = i + 91;
            int y = context.getScaledWindowHeight() - 23;
            context.drawGuiTexture(RenderLayer::getGuiTextured, SPELL_RIGHT_TEXTURE, x, y, offhandSlotWidth, 24);
            context.drawGuiTexture(RenderLayer::getGuiTextured, selectedSpell.getSlotTexture(), x + 10, y + 4, 16, 16);
            JSpellHelper.drawCooldownProgress(context,x + 10, y + 4, 16, 16, selectedSpell.getCooldown(), selectedSpell.getCooldownTimer());
        }
        context.getMatrices().pop();
    }

    private void renderManaBar(DrawContext context, int mana) {
        context.getMatrices().push();
        context.getMatrices().translate(0.0, 0.0, 3000.0);
        int maxMana = 100;
        int barWidth = 182;
        int barHeight = 5;
        int barXPos = (context.getScaledWindowWidth() / 2) - (barWidth / 2);
        int barYPos = context.getScaledWindowHeight() - 32 + 3;

        context.drawGuiTexture(RenderLayer::getGuiTextured, MANA_BAR_BACKGROUND_TEXTURE, barXPos, barYPos, barWidth, barHeight);
        if (mana > 0) {
            int progress = Math.max(1, (int)((float)barWidth / maxMana * mana));
            if (progress > 0) {
                context.drawGuiTexture(RenderLayer::getGuiTextured, MANA_BAR_PROGRESS_TEXTURE, barWidth, barHeight, 0, 0, barXPos, barYPos, progress, 5);
            }
        }
        context.getMatrices().pop();
    }

    private void renderManaBarText(DrawContext context, int mana) {
        context.getMatrices().push();
        context.getMatrices().translate(0.0, 0.0, 3000.0);
        String text = mana + "";
        int textX = (context.getScaledWindowWidth() - mc.textRenderer.getWidth(text)) / 2;
        int textY = (context.getScaledWindowHeight() - 32 + 3) - 6;
        JSpellHelper.drawOutlinedText(context, text, textX, textY);

        context.getMatrices().pop();
    }
}
























