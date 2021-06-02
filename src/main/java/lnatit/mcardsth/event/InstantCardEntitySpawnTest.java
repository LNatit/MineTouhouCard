package lnatit.mcardsth.event;

import lnatit.mcardsth.entity.InstantCardEntity;
import lnatit.mcardsth.item.InstantCard;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class InstantCardEntitySpawnTest
{
    @SubscribeEvent
    public static void onInsCardEntitySpawn(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();

        if (entity instanceof ItemEntity && ((ItemEntity) entity).getItem().getItem() instanceof InstantCard)
        {
            event.setCanceled(true);
            event.getWorld().addEntity(new InstantCardEntity((ItemEntity) entity));
        }
    }
}
