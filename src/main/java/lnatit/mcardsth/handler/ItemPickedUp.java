package lnatit.mcardsth.handler;

import lnatit.mcardsth.item.TenkyusPacket;
import lnatit.mcardsth.utils.PlayerPropertiesUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemPickedUp
{
    @SubscribeEvent
    public static void onItemPickedUp(PlayerEvent.ItemPickupEvent event)
    {
        if (event.getPlayer() instanceof ServerPlayerEntity && event.getStack().getItem() instanceof TenkyusPacket)
            PlayerPropertiesUtils.syncPlayerCards(event.getPlayer());
    }
}
