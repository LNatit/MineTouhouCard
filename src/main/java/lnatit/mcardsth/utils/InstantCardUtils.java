package lnatit.mcardsth.utils;

import lnatit.mcardsth.capability.PlayerProperties;
import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import lnatit.mcardsth.entity.InstantCardEntity;
import lnatit.mcardsth.item.InstantCard;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;

public class InstantCardUtils
{
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

    public static void triggerItemPickupTrigger(PlayerEntity playerIn, InstantCardEntity cardEntity)
    {
        PlayerEntity playerentity = cardEntity.getThrowerId() != null ? playerIn.world.getPlayerByUuid(cardEntity.getThrowerId()) : null;
        if (playerentity instanceof ServerPlayerEntity)
            CriteriaTriggers.THROWN_ITEM_PICKED_UP_BY_ENTITY.test((ServerPlayerEntity) playerentity, cardEntity.getCard(), playerIn);
    }

    public static void playerGetExtend(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(PlayerProperties::Extend);
    }

    public static void playerGetBomb(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(PlayerProperties::addSpell);
    }

    public static void playerGetExtend2(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(PlayerProperties::addLifeFragment);
    }

    public static void playerGetBomb2(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(PlayerProperties::addSpellFragment);
    }

    public static void playerGetPendulum(PlayerEntity player)
    {
        player.giveExperiencePoints(50);
    }

    public static void playerGetDango(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent((playerProperties) -> playerProperties.collectPower(0.50F));
    }

    public static void playerGetMokou(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        for (int i = 0; i < 3; i++)
            cap.ifPresent(PlayerProperties::Extend);
    }
}
