package lnatit.mcardsth.utils;

import lnatit.mcardsth.capability.PlayerProperties;
import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import lnatit.mcardsth.item.AbilityCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class AbilityCardUtils
{
    public static boolean doPlayerHold(PlayerEntity player, AbilityCard card)
    {
        if (player.inventory.offHandInventory.get(0).getItem() == card)
            return true;

        NonNullList<ItemStack> inventory = player.inventory.mainInventory;
        for (ItemStack itemStack : inventory)
            if (itemStack.getItem() == card)
                return true;
        return false;
    }

    public static boolean checkAutoBombActivation(ServerPlayerEntity player)
    {
        if (!doPlayerHold(player, (AbilityCard) ItemReg.AUTOBOMB.get()))
            return false;
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        PlayerProperties playerProperties = cap.orElse(null);
        if (playerProperties.canSpell(player))
        {
            playerProperties.canSpell(player);
            return true;
        } else return false;
    }

    public static void checkRokumon(LivingDeathEvent event, LivingEntity livingEntity)
    {

    }
}
