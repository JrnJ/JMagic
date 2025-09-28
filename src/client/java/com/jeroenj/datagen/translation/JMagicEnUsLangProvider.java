package com.jeroenj.datagen.translation;

import com.jeroenj.block.JMagicBlocks;
import com.jeroenj.item.JMagicItems;
import com.jeroenj.sound.JMagicSounds;
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
        translationBuilder.add(JMagicItems.KITSUNE_FLAME, "Kitsune Flame");

        translationBuilder.add(JMagicItems.EVORI_GUN, "Evori Gun");

        translationBuilder.add(JMagicItems.GOMU_GOMU_NO_MI, "Gomu Gomu no Mi");

        translationBuilder.add(JMagicItems.STRAWBERRY_ITEM, "Strawberry");
        translationBuilder.add(JMagicItems.GLUTINOUS_RICE_SEEDS, "Glutinous Rice Seeds");
        translationBuilder.add(JMagicItems.GLUTINOUS_RICE, "Glutinous Rice");
        translationBuilder.add(JMagicItems.MOCHI_MIXTURE, "Mochi Mixture");
        translationBuilder.add(JMagicItems.MOCHI_ITEM, "Mochi");
        translationBuilder.add(JMagicItems.GREEN_TEA_MOCHI_ITEM, "Green Tea Mochi");
        translationBuilder.add(JMagicItems.STRAWBERRY_MOCHI_ITEM, "Strawberry Mochi");
        translationBuilder.add(JMagicItems.CHOCOLATE_MOCHI_ITEM, "Chocolate Mochi");

        // Blocks
        translationBuilder.add(JMagicBlocks.MAGE_TABLE_BLOCK, "Mage Table");

        // Effects
        StatusEffectWithPotion(translationBuilder, "kitsune_flames", "Kitsune Flames");

        // Item Groups
        translationBuilder.add(ItemGroup("jmagic_group"), "JMagic");

        // Keys
        translationBuilder.add(Key("select_ability"), "Select Ability");
        translationBuilder.add(Key("quick_cast_1"), "Quick Cast 1");
        translationBuilder.add(Key("quick_cast_2"), "Quick Cast 2");
        translationBuilder.add(Key("quick_cast_3"), "Quick Cast 3");
        translationBuilder.add(Key("quick_cast_4"), "Quick Cast 4");
        translationBuilder.add(Key("inspect_item"), "Inspect Item");

        // Categories
        translationBuilder.add(Category("jmagic"), "JMagic");

        // Sounds
        translationBuilder.add(Sound("cartoon_boing"), "Cartoon Boing");

        translationBuilder.add(Sound("evori_inspect"), "Evori Inspect");
        translationBuilder.add(Sound("evori_reload"), "Evori Reload");
        translationBuilder.add(Sound("evori_shoot"), "Evori Shoot");
        translationBuilder.add(Sound("evori_select"), "Evori Select");
    }
}
