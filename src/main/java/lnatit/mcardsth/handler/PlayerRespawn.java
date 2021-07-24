package lnatit.mcardsth.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static deeplake.idlframework.idlnbtutils.IDLNBT.syncAll;
import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class PlayerRespawn
{
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity)
            syncAll((ServerPlayerEntity) player);
    }
}
