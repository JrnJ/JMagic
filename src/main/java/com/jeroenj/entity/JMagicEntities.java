package com.jeroenj.entity;

import com.jeroenj.JMagic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class JMagicEntities {

    public static final Identifier METEOR_ENTITY = JMagic.id("meteor");
    public static final EntityType<MeteorEntity> METEOR = Registry.register(
            Registries.ENTITY_TYPE, METEOR_ENTITY,
            EntityType.Builder.create(MeteorEntity::new, SpawnGroup.MISC)
                    .dimensions(1.0f, 1.0f)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, METEOR_ENTITY)));

    public static final Identifier MANA_BOLT_ENTITY = JMagic.id("mana_bolt");
    public static final EntityType<ManaBoltEntity> MANA_BOLT = Registry.register(
            Registries.ENTITY_TYPE, MANA_BOLT_ENTITY,
            EntityType.Builder.create(ManaBoltEntity::new, SpawnGroup.MISC)
                    .dimensions(1.0f, 1.0f)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, MANA_BOLT_ENTITY)));

    // Mobs
    public static final Identifier FAIRY_ENTITY = JMagic.id("fairy");
    public static final EntityType<FairyEntity> FAIRY = Registry.register(
            Registries.ENTITY_TYPE, FAIRY_ENTITY,
            EntityType.Builder.create(FairyEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1.0f, 1.0f)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, FAIRY_ENTITY)));

    public static void initialize() {

    }
}
