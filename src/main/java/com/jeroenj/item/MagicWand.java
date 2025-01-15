package com.jeroenj.item;

import com.jeroenj.JMagic;
import com.jeroenj.access.ClientSpellCaster;
import com.jeroenj.access.ServerPlayerEntityAccess;
import com.jeroenj.jspells.JSpellCastResult;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MagicWand extends Item {
    private static ClientSpellCaster clientSpellCaster;

    public MagicWand(Settings settings) {
        super(settings);
    }

    public static void setClientSpellCaster(ClientSpellCaster caster) {
        clientSpellCaster = caster;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            clientSpellCaster.castSpell();
        }

        return ActionResult.SUCCESS;
    }
}
