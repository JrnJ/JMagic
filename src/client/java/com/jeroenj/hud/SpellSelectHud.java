package com.jeroenj.hud;

import com.jeroenj.JMagic;
import com.jeroenj.JSpellHelper;
import com.jeroenj.access.ClientPlayerEntityAccess;
import com.jeroenj.jspells.JSpell;
import com.jeroenj.jspells.JSpellManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static com.jeroenj.JMagicClient.mc;

public class SpellSelectHud implements HudRenderCallback {
    public static final Identifier SPELL_INFO_BACKGROUND = JMagic.id("hud/spell_info_background");
    public static final Identifier CATEGORY_BACKGROUND = JMagic.id("hud/category_background");
    public static final Identifier CATEGORY_BACKGROUND_SELECTED_OVERLAY = JMagic.id("hud/category_background_selected_overlay");

    public static final int WHEEL_RADIUS = 60;
    public static final int SLOT_COUNT = 8;
    public static final double ANGEL_PER_SLOT = 360.0 / (double)SLOT_COUNT;

    private static boolean showAbilitySelectHud = false;
    private static int categoryIndex = 0;
    private static final int CATEGORY_COUNT = 8;
    private static double startMouseX;
    private static double startMouseY;
    private static int selectedSlotIndex;

    private final List<SpellSelectSlotWidget> slots = new ArrayList<>();
    private SpellSelectSlotWidget currentSlot;

    public SpellSelectHud() {
        for (int i = 0; i < JSpellManager.SPELL_COUNT; i++) {
            slots.add(new SpellSelectSlotWidget(i));
        }
        currentSlot = slots.getFirst();
    }

    public static void show() {
        // Hide crosshair
        showAbilitySelectHud = true;
        mc.mouse.unlockCursor();
    }

    // Returns selected index
    public static int hide() {
        showAbilitySelectHud = false;
        mc.mouse.lockCursor();
        return selectedSlotIndex;
    }

    public static boolean isShowing() {
        return showAbilitySelectHud;
    }

    public static void increaseCategory() {
        if (categoryIndex < CATEGORY_COUNT - 1) {
            categoryIndex++;
        } else {
            categoryIndex = 0;
        }
    }

    public static void decreaseCategory() {
        if (categoryIndex > 0) {
            categoryIndex--;
        } else {
            categoryIndex = CATEGORY_COUNT - 1;
        }
    }

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter renderTickCounter) {
        if (!showAbilitySelectHud) {
            return;
        }

        context.getMatrices().push();
        // Wheel
        int originX = context.getScaledWindowWidth() / 2;
        int originY = context.getScaledWindowHeight() / 2;

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

        // Selecting Logic
        selectedSlotIndex = (int) ((mouseAngle + ANGEL_PER_SLOT / 2.0) % 360.0 / ANGEL_PER_SLOT);
        SpellSelectSlotWidget selectedSlot = slots.get(selectedSlotIndex);

        // If selected slot changed
        if (selectedSlot != this.currentSlot) {
            this.currentSlot.unselect();
            this.currentSlot = selectedSlot;
            this.currentSlot.select(renderTickCounter);
        }

        // Render Logic
        for (int i = 0; i < SLOT_COUNT; i++) {
            JSpell spell = ((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager().getSpell(i);
            SpellSelectSlotWidget slotWidget = slots.get(i);
            slotWidget.setSpell(spell);
            slotWidget.render(context, renderTickCounter, originX, originY);
        }

//        if (selectedSlot.getSpell() != null) {
//            // Text
//            JSpell selectedSpell = selectedSlot.getSpell();
//            context.drawCenteredTextWithShadow(mc.textRenderer,
//                    Text.literal(selectedSpell.getName()).formatted(Formatting.BOLD), originX, originY - (int)(mc.textRenderer.fontHeight * 0.5), 0xFFFFFF);
//
//            // Info Screen
//            int infoMargin = 10;
//
//            int infoWidth = 100;
//            int infoHeight = WHEEL_RADIUS * 2 + SpellSelectSlotWidget.SLOT_SIZE;
//
//            int infoX = originX - (WHEEL_RADIUS + infoWidth + SpellSelectSlotWidget.SLOT_SIZE + infoMargin);
//            int infoY = originY - (infoHeight / 2);
//            context.drawGuiTexture(RenderLayer::getGuiTextured, SPELL_INFO_BACKGROUND,
//                    infoX, infoY,
//                    infoWidth, infoHeight
//            );
//
//            context.drawGuiTexture(RenderLayer::getGuiTextured, selectedSpell.getSlotTexture(),
//                    infoX + 4, infoY + 4,
//                    18, 18
//            );
//            context.drawTextWithShadow(mc.textRenderer,
//                    Text.literal(selectedSpell.getName()).formatted(Formatting.BOLD),
//                    infoX + 26, infoY + 4, 0xFFFFFFFF);
////            context.drawTextWithShadow(mc.textRenderer,
//////                    Text.literal(selectedSlot.getSpell().getName()).formatted(Formatting.AQUA),
//////                    infoX + 26, infoY + 4 + 10, 0xFFFFFFFF);
//            context.drawTextWithShadow(mc.textRenderer,
//                    Text.literal(selectedSpell.getDescription()),
//                    infoX + 4, infoY + 18 + 24, 0xFFFFFFFF);
//        }
        context.getMatrices().pop();

        // Categories
        renderCategories(context, renderTickCounter);
    }

    private void renderCategories(DrawContext context, RenderTickCounter renderTickCounter) {
        context.getMatrices().push();
        for (int i = 0; i < CATEGORY_COUNT; i++) {
            int categoryHeight = 16;
            int x = 100;
            int y = 100 + (categoryHeight * i);

            if (categoryIndex == i) {
                context.drawGuiTexture(RenderLayer::getGuiTextured, CATEGORY_BACKGROUND_SELECTED_OVERLAY,
                        x, y, 100, 12);
            } else {
                context.drawGuiTexture(RenderLayer::getGuiTextured, CATEGORY_BACKGROUND,
                        x, y, 100, 12);
            }

            context.drawCenteredTextWithShadow(mc.textRenderer,
                    "Category #" + (i + 1), x + 50, y + 2, 0xFFFFFFFF);
        }
        context.getMatrices().pop();
    }
}
