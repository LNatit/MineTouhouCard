package lnatit.mcardsth.handler;

import lnatit.mcardsth.utils.AbilityCardUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

//TODO overwrite using IDFNBT system.
@Mod.EventBusSubscriber(modid = MOD_ID)
public class PlayerTick
{
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        PlayerEntity player = event.player;
        if (event.phase == TickEvent.Phase.END && player instanceof ServerPlayerEntity)
            AbilityCardUtils.checkPlayerInventory((ServerPlayerEntity) player);
    }
}
