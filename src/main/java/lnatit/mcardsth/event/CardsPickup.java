package lnatit.mcardsth.event;

import lnatit.mcardsth.capabilities.PlayerProperties;
import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.instantCard;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.capabilities.PlayerPropertiesProvider.CPP_DEFAULT;
import static lnatit.mcardsth.item.ItemReg.*;

@Mod.EventBusSubscriber
public class CardsPickup
{
    @SubscribeEvent
    public static void onCardsPickup(EntityItemPickupEvent event)
    {
        PlayerEntity player = event.getPlayer();
        Item item = event.getItem().getItem().getItem();
        if (item instanceof AbstractCard)
        {
            if (item instanceof instantCard)
            {

            }
        }
    }

    public static void instantCardHandler(PlayerEntity player, instantCard card)
    {
        if (EXTEND.get().equals(card))
        {
            playerGetExtend(player);
        } else if (BOMB.get().equals(card))
        {
            playerGetBomb(player);
        } else if (EXTEND2.get().equals(card))
        {
            playerGetExtend2(player);
        } else if (BOMB2.get().equals(card))
        {
            playerGetBomb2(player);
        } else if (PENDULUM.get().equals(card))
        {
            playerGetPendulum(player);
        } else if (DANGO.get().equals(card))
        {
            playerGetDango(player);
        } else if (MOKOU.get().equals(card))
        {
            playerGetMokou(player);
        }
    }

    public static void playerGetExtend(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(CPP_DEFAULT);
        cap.ifPresent(PlayerProperties::Extend);
    }

    public static void playerGetBomb(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(CPP_DEFAULT);
        cap.ifPresent(PlayerProperties::addSpell);
    }

    public static void playerGetExtend2(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(CPP_DEFAULT);
        cap.ifPresent(PlayerProperties::addLifeFragment);
    }

    public static void playerGetBomb2(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(CPP_DEFAULT);
        cap.ifPresent(PlayerProperties::addSpellFragment);
    }

    public static void playerGetPendulum(PlayerEntity player)
    {

    }

    public static void playerGetDango(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(CPP_DEFAULT);
        cap.ifPresent((playerProperties) -> playerProperties.collectPower(0.50F));
    }

    public static void playerGetMokou(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(CPP_DEFAULT);
        for (int i = 0; i < 3; i++)
            cap.ifPresent(PlayerProperties::Extend);
    }
}
