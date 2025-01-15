package com.jeroenj.jspells;

import com.jeroenj.attachments.JMagicAttachmentTypes;
import com.jeroenj.attachments.JMagicManaAttachment;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class JSpellManager {
    public static final int SPELL_COUNT = 8;
    private JSpell selectedSpell;

    private final List<JSpell> spells = Arrays.asList(new JSpell[SPELL_COUNT]);

    public JSpellManager(List<JSpell> spells) {
        for (int i = 0; i < spells.size(); i++) {
            this.spells.set(i, spells.get(i));
        }

        selectedSpell = this.spells.getFirst();
    }

    public void setSpell(int index, Identifier identifier) {
        if (index >= SPELL_COUNT) {
            return;
        }
        this.spells.set(index, JSpellRegistry.getSpell(identifier));
    }

    public void tick(Entity user) {
        int currentMana = user.getAttachedOrCreate(JMagicAttachmentTypes.PLAYER_MANA).getCurrentMana();
        if (currentMana < 100) {
            currentMana = Math.clamp(currentMana + 1, 0, 100);
            user.setAttached(JMagicAttachmentTypes.PLAYER_MANA, new JMagicManaAttachment(currentMana));
        }

        for (JSpell spell : spells) {
            if (spell == null) continue;
            spell.tick();
        }
    }

    public void selectSpell(int index) {
        if (index < spells.size() && spells.get(index) instanceof JSpell spell) {
            selectedSpell = spell;
        } else {
            System.out.println("No spell here ^^");
        }
    }

    public JSpellCastResult cast(ServerWorld world, ServerPlayerEntity user, Identifier spellId) {
        return getSpell(spellId).cast(world, user);
    }

    public JSpell getSpell(int index) {
        if (index < spells.size()) {
            return spells.get(index);
        } else {
            return null;
        }
    }

    public JSpell getSpell(Identifier identifier) {
        for (JSpell spell : this.spells) {
            if (spell == null) continue;

            Identifier id = spell.getId();
            if (id.equals(identifier)) {
                return spell;
            }
        }

        return null;
    }

    public JSpell getSelected() {
        return selectedSpell;
    }
}
