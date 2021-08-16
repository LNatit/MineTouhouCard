package lnatit.mcardsth.utils;

import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.EasterCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.RegistryObject;

import static deeplake.idlframework.idlnbtutils.IDLNBT.*;
import static deeplake.idlframework.idlnbtutils.IDLNBTConst.*;
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
        else return getPlayerIdeallandBoolSafe(player, card.getRegistryName().getPath());
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

        if (!(card instanceof EasterCard) && !updatePlayerCardsTotal(player))
            return false;

        setPlayerIdeallandTagSafe(player, card.getRegistryName().getPath(), true);
        return true;
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
        setPlayerIdeallandTagSafe(player, card.getRegistryName().getPath(), doCollect);
        return true;
    }

    /**
     * get player's number of cards collected
     *
     * @param player player to check
     * @return number of cards collected
     */
    public static int playerCardsTotal(PlayerEntity player)
    {
        return getPlayerIdeallandIntSafe(player, COUNT);
    }

    private static boolean updatePlayerCardsTotal(PlayerEntity player)
    {
        int count = getPlayerIdeallandIntSafe(player, COUNT);
        if (count == CARDS_TOTAL)
        {
            Warn("Cards count overSize!(now cards count is %d of %d)", count, CARDS_TOTAL);
            return false;
        }
        setPlayerIdeallandTagSafe(player, COUNT, count + 1);
        return true;
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
            if (item instanceof AbstractCard && !(item instanceof EasterCard) && getPlayerIdeallandBoolSafe(player, key))
            {
                count++;
                nbt.putBoolean(key, true);
            }
            else nbt.putBoolean(key, false);
        }
        int init = getPlayerIdeallandIntSafe(player, COUNT);

        if (init == count)
            return true;
        else
        {
            if (doFix)
            {
                nbt.putInt(COUNT, count);
                getPlayerIdlTagSafe(player).merge(nbt);
                if (player instanceof ServerPlayerEntity)
                    sync((ServerPlayerEntity) player, nbt);
            }
            return false;
        }
    }

    public static boolean doPlayersAbilityEnabled(PlayerEntity player)
    {
        return getPlayerIdeallandBoolSafe(player, ENABLE);
    }

    public static void enablePlayerAbility(PlayerEntity player, boolean enable)
    {
        setPlayerIdeallandTagSafe(player, ENABLE, enable);
    }
}
