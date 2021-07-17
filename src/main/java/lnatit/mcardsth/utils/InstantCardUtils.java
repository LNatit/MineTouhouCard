package lnatit.mcardsth.utils;

import lnatit.mcardsth.capability.PlayerProperties;
import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import lnatit.mcardsth.entity.InstantCardEntity;
import lnatit.mcardsth.item.InstantCard;
import lnatit.mcardsth.network.BarRenderPacket;
import lnatit.mcardsth.network.NetworkManager;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class InstantCardUtils
{
    public static boolean instantCardHandler(@Nonnull PlayerEntity player, InstantCard card)
    {
        boolean flag = false;

        if (card.getRegistryName() != null)
        {
            String cardName = card.getRegistryName().getPath();

            switch (cardName)
            {
                case "extend":
                    flag = playerGetExtend(player);
                    break;
                case "bomb":
                    flag = playerGetBomb(player);
                    break;
                case "extend2":
                    flag = playerGetExtend2(player);
                    break;
                case "bomb2":
                    flag = playerGetBomb2(player);
                    break;
                case "pendulum":
                    flag = playerGetPendulum(player);
                    break;
                case "dango":
                    flag = playerGetDango(player);
                    break;
                case "mokou":
                    flag = playerGetMokou(player);
                    break;
            }
        }

        return flag;
    }

    public static void triggerItemPickupTrigger(PlayerEntity playerIn, InstantCardEntity cardEntity)
    {
        PlayerEntity playerentity = cardEntity.getThrowerId() != null ? playerIn.world.getPlayerByUuid(cardEntity.getThrowerId()) : null;
        if (playerentity instanceof ServerPlayerEntity)
            CriteriaTriggers.THROWN_ITEM_PICKED_UP_BY_ENTITY.test((ServerPlayerEntity) playerentity, cardEntity.getCard(), playerIn);
    }

    public static boolean playerGetExtend(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        PlayerProperties playerProperties = cap.orElse(null);
        return playerProperties.Extend(player);
    }

    public static boolean playerGetBomb(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(playerProperties -> playerProperties.addSpell(player));
        return true;
    }

    public static boolean playerGetExtend2(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        PlayerProperties playerProperties = cap.orElse(null);
        return playerProperties.addLifeFragment(player);
    }

    public static boolean playerGetBomb2(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(playerProperties -> playerProperties.addSpellFragment(player));
        return true;
    }

    public static boolean playerGetPendulum(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent(playerProperties -> playerProperties.collectMoney(player, 0.50F));
        return true;
    }

    public static boolean playerGetDango(PlayerEntity player)
    {
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        cap.ifPresent((playerProperties) -> playerProperties.collectPower(player, 0.50F));
        return true;
    }

    public static boolean playerGetMokou(PlayerEntity player)
    {
        boolean flag = false;
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        PlayerProperties playerProperties = cap.orElse(null);
        for (int i = 0; i < 3; i++)
            flag = playerProperties.Extend(player) || flag;
        return flag;
    }
}
