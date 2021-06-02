package lnatit.mcardsth.event;

import lnatit.mcardsth.capabilities.PlayerProperties;
import lnatit.mcardsth.capabilities.PlayerPropertiesProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;
import static lnatit.mcardsth.capabilities.PlayerPropertiesProvider.CPP_DEFAULT;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommonEventHandler
{
    @SubscribeEvent
    public static void onAttachCapabilityEvent(AttachCapabilitiesEvent<Entity> event)
    {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity)
            event.addCapability(new ResourceLocation(MOD_ID,"player_properties"), new PlayerPropertiesProvider((PlayerEntity) entity));
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        if (!event.isWasDeath())
        {
            LazyOptional<PlayerProperties> old_pp = event.getOriginal().getCapability(CPP_DEFAULT);
            LazyOptional<PlayerProperties> new_pp = event.getEntity().getCapability(CPP_DEFAULT);

            if (old_pp.isPresent() && new_pp.isPresent())
            {
                new_pp.ifPresent(
                        (newCap) ->
                                old_pp.ifPresent(
                                        (oldCap) ->
                                                newCap.deserializeNBT(
                                                        oldCap.serializeNBT()
                                                )
                                        )
                        );
            }
        }
    }
}
