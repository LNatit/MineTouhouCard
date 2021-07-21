package lnatit.mcardsth.handler;

import lnatit.mcardsth.entity.CardEntity;
import lnatit.mcardsth.item.AbstractCard;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;
import static net.minecraft.item.Items.DEBUG_STICK;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CardEntitySpawn
{
    @SubscribeEvent
    public static void onCardEntitySpawn(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();

        if (entity instanceof ItemEntity && ((ItemEntity) entity).getItem().getItem() instanceof AbstractCard)
        {
            CardEntity cardEntity = new CardEntity((ItemEntity) entity);
            event.setCanceled(true);
            event.getWorld().addEntity(cardEntity);

            UUID uuid = ((ItemEntity) entity).getThrowerId();
            if (uuid != null)
            {
                PlayerEntity player = event.getWorld().getPlayerByUuid(uuid);
                if (player instanceof ServerPlayerEntity)
                {
                    Item item = player.inventory.offHandInventory.get(0).getItem();
                    if (item == DEBUG_STICK)
                    {
                        cardEntity.setNoDespawn();
//                        cardEntity.noClip = true;
                        cardEntity.setNoGravity(true);
                        cardEntity.setInvulnerable(true);
                        cardEntity.entityCollisionReduction = 1F;
                    }
                }
            }
//            } else
//            {
//                entity.setCustomNameVisible(true);
//                if (card instanceof AttributeCard)
//                    ((ItemEntity) entity).setNoDespawn();
//                    else ((ItemEntity) entity).lifespan = 1200;
        }
    }
}

