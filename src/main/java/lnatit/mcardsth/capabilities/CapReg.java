package lnatit.mcardsth.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nullable;

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
