package com.jeroenj.hud;

import com.jeroenj.JMagic;
import com.jeroenj.JSpellHelper;
import com.jeroenj.access.ClientPlayerEntityAccess;
import com.jeroenj.jspells.JSpell;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static com.jeroenj.JMagicClient.mc;

public class SpellSelectHud {
    public static final Identifier SPELL_SELECTION_SLOT_TEXTURE = JMagic.id("hud/spell_selection_slot");
    public static final Identifier SPELL_SELECTION_SLOT_SELECTED_TEXTURE = JMagic.id("hud/spell_selection_slot_selected");

    public static final int WHEEL_RADIUS = 60;
    public static final int SLOT_COUNT = 8;
    private static final double ANGEL_PER_SLOT = 360.0 / (double)SLOT_COUNT;

    private static boolean showAbilitySelectHud = false;
    private static double startMouseX;
    private static double startMouseY;
    private static int selectedSlot;

    public static void show() {
        // Hide crosshair
        showAbilitySelectHud = true;
        mc.mouse.unlockCursor();
    }

    // Returns selected index
    public static int hide() {
        showAbilitySelectHud = false;
        mc.mouse.lockCursor();
        return selectedSlot;
    }

    public static boolean isShowing() {
        return showAbilitySelectHud;
    }

    // Also on scroll switch slot - Mex
    // Second ring with 12(x) extra slots - Mex
    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        context.getMatrices().push();
        if (showAbilitySelectHud) {
            int originX = (int)((double)context.getScaledWindowWidth() * 0.5);
            int originY = (int)((double)context.getScaledWindowHeight() * 0.5);

            double screenCenterX = mc.getWindow().getWidth() * 0.5;
            double screenCenterY = mc.getWindow().getHeight() * 0.5;

            double mouseX = mc.mouse.getX();
            double mouseY = mc.mouse.getY();

            double deltaX = mouseX - screenCenterX;
            double deltaY = mouseY - screenCenterY;
            double mouseAngle = Math.toDegrees(Math.atan2(deltaY, deltaX)) + 90.0;
            if (mouseAngle < 0.0) {
                mouseAngle += 360.0;
            }

            selectedSlot = (int) ((mouseAngle + ANGEL_PER_SLOT / 2.0) % 360.0 / ANGEL_PER_SLOT);

            for (int i = 0; i < SLOT_COUNT; i++) {

                double angleRad = Math.toRadians(i * ANGEL_PER_SLOT - 90.0);
                int slotX = (int) (originX + Math.cos(angleRad) * WHEEL_RADIUS);
                int slotY = (int) (originY + Math.sin(angleRad) * WHEEL_RADIUS);

                if (i == selectedSlot)
                {
                    int size = 30;
                    context.drawGuiTexture(RenderLayer::getGuiTextured,
                            SPELL_SELECTION_SLOT_SELECTED_TEXTURE,
                            slotX - size / 2, slotY - size / 2,
                            size, size
                    );
                }
                else {
                    context.drawGuiTexture(RenderLayer::getGuiTextured,
                            SPELL_SELECTION_SLOT_TEXTURE,
                            slotX - 12, slotY - 12,
                            24, 24
                    );
                }


                if (((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().getSpell(i) instanceof JSpell spell) {
                    context.drawGuiTexture(RenderLayer::getGuiTextured,
                        spell.getSlotTexture(),
                            slotX - 8, slotY - 8,
                            16, 16
                            );

                    JSpellHelper.drawCooldownProgress(context, slotX - 11, slotY - 11, 22, 22, spell.getCooldown(), spell.getCooldownTimer());

                    if (selectedSlot == i) {
                        context.drawCenteredTextWithShadow(mc.textRenderer,
                                Text.literal(spell.getName()).formatted(Formatting.BOLD), originX, originY - (int)(mc.textRenderer.fontHeight * 0.5), 0xFFFFFF);
                    }
                }
            }
        }
        context.getMatrices().pop();
    }
}
