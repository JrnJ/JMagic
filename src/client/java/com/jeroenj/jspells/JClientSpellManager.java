package com.jeroenj.jspells;

import com.jeroenj.access.ClientPlayerEntityAccess;
import com.jeroenj.access.ClientSpellCaster;
import com.jeroenj.networking.payload.CastSpellData;
import com.jeroenj.networking.payload.CastSpellPayload;
import com.jeroenj.networking.payload.UsedSpellPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class JClientSpellManager implements ClientSpellCaster {
    private JSpell selectedSpell;
    private final List<JSpell> spells = Arrays.asList(new JSpell[JSpellManager.SPELL_COUNT]);

    public JClientSpellManager() {
        ClientPlayNetworking.registerGlobalReceiver(UsedSpellPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                JSpell spell = getSpell(payload.data().identifier());
                spell.setCooldownTimer(spell.getCooldown());
            });
        });
    }

    public void tick() {
        for (JSpell spell : spells) {
            if (spell == null) continue;
            spell.tick();
        }
    }

    public void setSpells(List<Identifier> spells) {
        for (int i = 0; i < spells.size(); i++) {
            this.spells.set(i, JSpellRegistry.getSpell(spells.get(i)));
        }
        selectedSpell = this.spells.get(0);
    }

    public void selectSpell(int index) {
        // Send Packet
        JSpell spell = spells.get(index);
        if (spell == null) {
            return;
        }
        selectedSpell = spell;
    }

    public void castSpell() {
        if (selectedSpell != null) {
            ClientPlayNetworking.send(new CastSpellPayload(new CastSpellData(selectedSpell.getId())));
        }
    }

    public void castSpell(int index) {
        ClientPlayNetworking.send(new CastSpellPayload(new CastSpellData(getSpell(index).getId())));
    }

    public JSpell getSelectedSpell() {
        return selectedSpell;
    }

    public JSpell getSpell(int index) {
        return this.spells.get(index);
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
}
