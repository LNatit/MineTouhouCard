package lnatit.mcardsth.handler;

import lnatit.mcardsth.network.BarRenderPacket;
import lnatit.mcardsth.network.NetworkManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class BarRender
{
    @SubscribeEvent
    public static void onPlayerLoggerIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        NetworkManager.serverSendToPlayer(new BarRenderPacket((byte) 1), (ServerPlayerEntity) event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        NetworkManager.serverSendToPlayer(new BarRenderPacket((byte) 1), (ServerPlayerEntity) event.getPlayer());
    }
}
