package com.jeroenj.hud;

import com.jeroenj.JMagic;
import com.jeroenj.JSpellHelper;
import com.jeroenj.jspells.JSpell;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class SpellSelectSlotWidget {
    public static final Identifier SPELL_SELECTION_SLOT_TEXTURE = JMagic.id("hud/spell_selection_slot");
    public static final Identifier SPELL_SELECTION_SLOT_SELECTED_TEXTURE = JMagic.id("hud/spell_selection_slot_selected");

    public static final int SLOT_SIZE = 24;

    private int index;
    private JSpell spell;

    private boolean selected;

    public SpellSelectSlotWidget(int index) {
        this.index = index;
    }

    public void select(RenderTickCounter renderTickCounter) {
        this.selected = true;
    }

    public void unselect() {
        this.selected = false;
    }

    public boolean selected() {
        return this.selected;
    }

    public void setSpell(JSpell spell) {
        this.spell = spell;
    }

    public JSpell getSpell() {
        return this.spell;
    }

    public void render(DrawContext context, RenderTickCounter renderTickCounter, int originX, int originY) {
        double angleRad = Math.toRadians(index * SpellSelectHud.ANGEL_PER_SLOT - 90.0);
        int slotX = (int) (originX + Math.cos(angleRad) * SpellSelectHud.WHEEL_RADIUS);
        int slotY = (int) (originY + Math.sin(angleRad) * SpellSelectHud.WHEEL_RADIUS);

        context.drawGuiTexture(RenderLayer::getGuiTextured,
                selected ? SPELL_SELECTION_SLOT_SELECTED_TEXTURE : SPELL_SELECTION_SLOT_TEXTURE,
                slotX - (SLOT_SIZE / 2), slotY - (SLOT_SIZE / 2),
                SLOT_SIZE, SLOT_SIZE
        );

        if (spell != null) {
            context.drawGuiTexture(RenderLayer::getGuiTextured,
                    spell.getSlotTexture(),
                    slotX - 8, slotY - 8,
                    16, 16
            );

            JSpellHelper.drawCooldownProgress(context, slotX - 11, slotY - 11, 22, 22, spell.getCooldown(), spell.getCooldownTimer());
        }
    }
}
