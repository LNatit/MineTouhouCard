package lnatit.mcardsth.utils;

import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.RegistryObject;

import static deeplake.idlframework.idlnbtutils.IDLNBTConst.*;
import static deeplake.idlframework.idlnbtutils.IDLNBTUtils.*;
import static lnatit.mcardsth.LogUtils.*;

public class PlayerPropertiesUtils
{
    public static final int CARDS_TOTAL = 56;

    /**
     * check whether player have already collected a card
     *
     * @param player player to check
     * @param card   card to check
     * @return player's collection status of given card
     */
    public static boolean doPlayerCollected(PlayerEntity player, AbstractCard card)
    {
        if (card.getRegistryName() == null)
        {
            Warn("Trying to check an unregistered Card!");
            return true;
        }
        else return GetBoolean(player, card.getRegistryName().getPath(), false);
    }

    /**
     * called when player collects a card
     *
     * @param player player in operation
     * @param card   card which player gets
     * @return whether the operation success or not
     */
    public static boolean collectCard(PlayerEntity player, AbstractCard card)
    {
        if (card.getRegistryName() == null)
        {
            Warn("Trying to collect an unregistered Card!");
            return false;
        }

        if (!updatePlayerCardsTotal(player))
            return false;

        return SetBoolean(player, card.getRegistryName().getPath(), true);
    }

    /**
     * called when player's property updates
     *
     * @param player    player in operation
     * @param card      card which player gets
     * @param doCollect card's collection status
     * @return whether the operation success or not
     */
    public static boolean collectCardUnsafe(PlayerEntity player, AbstractCard card, boolean doCollect)
    {
        if (card.getRegistryName() == null)
        {
            Warn("Trying to collect an unregistered Card!");
            return false;
        }
        return SetBoolean(player, card.getRegistryName().getPath(), doCollect);
    }

    /**
     * get player's number of cards collected
     *
     * @param player player to check
     * @return number of cards collected
     */
    public static int playerCardsTotal(PlayerEntity player)
    {
        return GetInt(player, COUNT, 0);
    }

    private static boolean updatePlayerCardsTotal(PlayerEntity player)
    {
        int count = GetInt(player, COUNT, 0);
        if (count == CARDS_TOTAL)
        {
            Warn("Cards count overSize!(now cards count is %d of %d)", count, CARDS_TOTAL);
            return false;
        }
        return SetInt(player, COUNT, count + 1);
    }

    public static void syncPlayerCards(PlayerEntity player)
    {
        if (player instanceof ServerPlayerEntity)
            syncAll((ServerPlayerEntity) player);
    }

    public static boolean playerDataCheck(PlayerEntity player, boolean doFix)
    {
        CompoundNBT nbt = new CompoundNBT();

        int count = 0;
        for (RegistryObject<Item> ItemObj : ItemReg.ITEMS.getEntries())
        {
            Item item = ItemObj.get();
            String key = item.getRegistryName().getPath();
            if (item instanceof AbstractCard && GetBoolean(player, key, false))
            {
                count++;
                nbt.putBoolean(key, true);
            }
            else nbt.putBoolean(key, false);
        }
        int init = GetInt(player, COUNT, 0);

        if (init == count)
            return true;
        else
        {
            if (doFix)
            {
                nbt.putInt(COUNT, count);
                player.getPersistentData().merge(nbt);
                if (player instanceof ServerPlayerEntity)
                    sync((ServerPlayerEntity) player, nbt);
            }
            return false;
        }

    }
}
