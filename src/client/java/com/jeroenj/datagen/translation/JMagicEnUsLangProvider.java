package com.jeroenj.datagen.translation;

import com.jeroenj.block.JMagicBlocks;
import com.jeroenj.item.JMagicItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class JMagicEnUsLangProvider extends JMagicLanguageProvider {
    public JMagicEnUsLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        // Items
        translationBuilder.add(JMagicItems.MAGIC_WAND, "Magic Wand");
        translationBuilder.add(JMagicItems.MAGE_HOOD, "Mage Hood");
        translationBuilder.add(JMagicItems.MAGE_ROBE, "Mage Robe");
        translationBuilder.add(JMagicItems.MAGE_LEGGINGS, "Mage Leggings");
        translationBuilder.add(JMagicItems.MAGE_BOOTS, "Mage Boots");
        translationBuilder.add(JMagicItems.SPELL_SCROLL, "Spell Scroll");
        translationBuilder.add(JMagicItems.FIREBALL_SPELL_SCROLL, "Fireball Spell Scroll");

        // Blocks
        translationBuilder.add(JMagicBlocks.MAGE_TABLE_BLOCK, "Mage Table");

        // Item Groups
        translationBuilder.add(ItemGroup("jmagic_group"), "JMagic");

        // Keys
        translationBuilder.add(Key("select_ability"), "Select Ability");
        translationBuilder.add(Key("quick_cast_1"), "Quick Cast 1");
        translationBuilder.add(Key("quick_cast_2"), "Quick Cast 2");
        translationBuilder.add(Key("quick_cast_3"), "Quick Cast 3");
        translationBuilder.add(Key("quick_cast_4"), "Quick Cast 4");

        // Categories
        translationBuilder.add(Category("jmagic"), "JMagic");
    }
}
