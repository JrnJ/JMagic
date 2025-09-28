package com.jeroenj.item;

import com.jeroenj.sound.JMagicSounds;
import com.jeroenj.util.JHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EvoriGunItem extends GunItem {
    public static final int RELOAD_TIME = 50; // 2.5s
    public static final int INSPECT_TIME = 90; // 4.5s

    public EvoriGunItem(Settings settings) {
        super(settings, RELOAD_TIME, INSPECT_TIME,
                JMagicSounds.EVORI_RELOAD, JMagicSounds.EVORI_SELECT, JMagicSounds.EVORI_INSPECT, JMagicSounds.EVORI_SHOOT);
    }

//    @Override
//    public ActionResult use(World world, PlayerEntity user, Hand hand) {
//        return super.use(world, user, hand);
//        if (world.isClient()) {
//            return ActionResult.SUCCESS;
//        }
//
//        using = true;
//        ServerPlayerEntity serverPlayer = (ServerPlayerEntity)user;
//        JHelper.playServerSound(serverPlayer, JMagicSounds.EVORI_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);
//        System.out.println("Finished shooting");
//
//        return ActionResult.SUCCESS;
//    }

    //    @Override
//    public void onInMainHand(PlayerEntity player) {
//        super.onInMainHand(player);
//    }

    //    @Override
//    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
//        using = false;
//        System.out.println("Finished shooting");
//
//        return true;
//    }
}
