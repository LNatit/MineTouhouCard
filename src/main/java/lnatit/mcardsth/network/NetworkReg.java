package lnatit.mcardsth.network;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;
import static lnatit.mcardsth.network.NetworkManager.registerPacket;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkReg
{
    @SubscribeEvent
    public static void onNetworkRegister(FMLCommonSetupEvent event)
    {
        registerPacket();
    }
}
