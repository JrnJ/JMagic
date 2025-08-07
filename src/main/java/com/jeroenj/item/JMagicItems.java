package com.jeroenj.item;

import com.jeroenj.JMagic;
import com.jeroenj.armor.JMagicArmorMaterials;
import com.jeroenj.entity.JMagicEntities;
import com.jeroenj.item.MagicWand;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public final class JMagicItems {

    // Items
    public static final Item MAGIC_WAND = register("magic_wand", MagicWand::new, new Item.Settings()
            .component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true))
            .component(DataComponentTypes.MAX_STACK_SIZE, 1)
    );

    public static final Item MAGE_HOOD = register("mage_hood",
            (settings) -> new ArmorItem(JMagicArmorMaterials.MAGE_ARMOR_MATERIAL, EquipmentType.HELMET, settings), new Item.Settings()
    );
    public static final Item MAGE_ROBE = register("mage_robe",
            (settings) -> new ArmorItem(JMagicArmorMaterials.MAGE_ARMOR_MATERIAL, EquipmentType.CHESTPLATE, settings), new Item.Settings()
    );
    public static final Item MAGE_LEGGINGS = register("mage_leggings",
            (settings) -> new ArmorItem(JMagicArmorMaterials.MAGE_ARMOR_MATERIAL, EquipmentType.LEGGINGS, settings), new Item.Settings()
    );
    public static final Item MAGE_BOOTS = register("mage_boots",
            (settings) -> new ArmorItem(JMagicArmorMaterials.MAGE_ARMOR_MATERIAL, EquipmentType.BOOTS, settings), new Item.Settings()
    );

    public static final Item KITSUNE_FLAME = register("kitsune_flame", Item::new, new Item.Settings());

    // Scrolls
    public static final Item SPELL_SCROLL = register("spell_scroll", Item::new, new Item.Settings()
            .component(DataComponentTypes.MAX_STACK_SIZE, 16)
    );

    public static final Item FIREBALL_SPELL_SCROLL = register("fireball_spell_scroll", Item::new, new Item.Settings()
            .component(DataComponentTypes.MAX_STACK_SIZE, 16)
    );

    // Fruits
    public static final Item GOMU_GOMU_NO_MI = register("gomu_gomu_no_mi", DevilFruitItem::new, new Item.Settings()
            .component(DataComponentTypes.MAX_STACK_SIZE, 1)
    );

    // Methods
    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, JMagic.id(path));
        return Items.register(registryKey, factory, settings);
    }

    public static void initialize() { }
}
