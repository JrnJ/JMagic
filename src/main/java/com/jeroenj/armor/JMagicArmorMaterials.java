package com.jeroenj.armor;

import com.jeroenj.JMagic;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;

public final class JMagicArmorMaterials {
    public static final ArmorMaterial MAGE_ARMOR_MATERIAL = new ArmorMaterial(
            500, Util.make(new EnumMap<>(EquipmentType.class), map -> {
                map.put(EquipmentType.HELMET, 4);
                map.put(EquipmentType.CHESTPLATE, 6);
                map.put(EquipmentType.LEGGINGS, 2);
                map.put(EquipmentType.BOOTS, 2);
                map.put(EquipmentType.BODY, 4);
            }),
            20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0,0,
            // TODO find out what i, f, g and TagKey do
            TagKey.of(RegistryKeys.ITEM, JMagic.id("mage_repair")), EquipmentAssetKeys.register("mage_armor_material"));

    public static void initialize() {

    }
}
