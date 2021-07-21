package lnatit.mcardsth.capability;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Deprecated
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapReg
{
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event)
    {
        event.enqueueWork(
                () -> CapabilityManager
                        .INSTANCE
                        .register(PlayerProperties.class, PlayerPropertiesProvider.storage, PlayerProperties.factory)
        );
    }
}
