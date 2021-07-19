package lnatit.mcardsth.handler;

import lnatit.mcardsth.entity.InstantCardEntity;
import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.AttributeCard;
import lnatit.mcardsth.item.InstantCard;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CardEntitySpawn
{
    @SubscribeEvent
    public static void onCardEntitySpawn(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();

        if (entity instanceof ItemEntity && ((ItemEntity) entity).getItem().getItem() instanceof AbstractCard)
        {
            AbstractCard card = (AbstractCard) ((ItemEntity) entity).getItem().getItem();
            if (card instanceof InstantCard)
            {
                event.setCanceled(true);
                event.getWorld().addEntity(new InstantCardEntity((ItemEntity) entity));
            } else
            {
                entity.setCustomNameVisible(true);
                if (card instanceof AttributeCard)
                    ((ItemEntity) entity).setNoDespawn();
                    else ((ItemEntity) entity).lifespan = 1200;
            }
        }
    }
}
