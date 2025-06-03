package com.jeroenj;

import com.jeroenj.block.JMagicBlocks;
import com.jeroenj.item.JMagicItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class JMagicItemGroups {
    public static final ItemGroup JMAGIC_GROUP = Registry.register(
            Registries.ITEM_GROUP, JMagic.id("jmagic_group"), FabricItemGroup.builder()
            .icon(() -> new ItemStack(JMagicItems.MAGIC_WAND))
            .displayName(JMagic.text("itemGroup", "jmagic_group"))
            .entries((context, entries) -> {
                entries.add(JMagicItems.MAGIC_WAND);
                entries.add(JMagicItems.MAGE_HOOD);
                entries.add(JMagicItems.MAGE_ROBE);
                entries.add(JMagicItems.MAGE_LEGGINGS);
                entries.add(JMagicItems.MAGE_BOOTS);
                entries.add(JMagicBlocks.MAGE_TABLE_BLOCK);
                entries.add(JMagicItems.KITSUNE_FLAME);

                // All Scrolls at the end
                entries.add(JMagicItems.SPELL_SCROLL);
                entries.add(JMagicItems.FIREBALL_SPELL_SCROLL);
            })
            .build());

    public static void initialize() { }
}
