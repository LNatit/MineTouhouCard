package lnatit.mcardsth.handler;

import lnatit.mcardsth.capability.PlayerProperties;
import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.InstantCard;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Deprecated
@Mod.EventBusSubscriber(modid = MOD_ID)
public class CardsPickup
{
    @SubscribeEvent
    public static void onCardsPickup(EntityItemPickupEvent event)
    {
        PlayerEntity player = event.getPlayer();
        Item item = event.getItem().getItem().getItem();
        if (item instanceof AbstractCard)
        {
            if (item instanceof InstantCard)
                instantCardHandler(player, (InstantCard) item);
        }

    }

    public static void instantCardHandler(PlayerEntity player, InstantCard card)
    {
        if (card.getRegistryName() != null)
        {
            String cardName = card.getRegistryName().getPath();
            switch (cardName)
            {
                case "extend":
                    playerGetExtend(player);
                    break;
                case "bomb":
                    playerGetBomb(player);
                    break;
                case "extend2":
                    playerGetExtend2(player);
                    break;
                case "bomb2":
                    playerGetBomb2(player);
                    break;
                case "pendulum":
                    playerGetPendulum(player);
                    break;
                case "dango":
                    playerGetDango(player);
                    break;
                case "mokou":
                    playerGetMokou(player);
                    break;
            }
        }
    }

    public static void playerGetExtend(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(playerProperties -> playerProperties.Extend((ServerPlayerEntity) player));
    }

    public static void playerGetBomb(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(playerProperties -> playerProperties.addSpell((ServerPlayerEntity) player));
    }

    public static void playerGetExtend2(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(playerProperties -> playerProperties.addLifeFragment((ServerPlayerEntity) player));
    }

    public static void playerGetBomb2(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(playerProperties -> playerProperties.addSpellFragment((ServerPlayerEntity) player));
    }

    public static void playerGetPendulum(PlayerEntity player)
    {
        player.giveExperiencePoints(50);
    }

    public static void playerGetDango(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent((playerProperties) -> playerProperties.collectPower((ServerPlayerEntity) player, 0.50F));
    }

    public static void playerGetMokou(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        for (int i = 0; i < 3; i++)
            cap.ifPresent(playerProperties -> playerProperties.Extend((ServerPlayerEntity) player));
    }
}
