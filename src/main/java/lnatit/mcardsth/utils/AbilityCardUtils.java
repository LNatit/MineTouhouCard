package lnatit.mcardsth.utils;

import lnatit.mcardsth.capability.PlayerProperties;
import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import lnatit.mcardsth.item.AbilityCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class AbilityCardUtils
{
    public static boolean doPlayerHold(PlayerEntity player, Item card)
    {
        if (player.inventory.offHandInventory.get(0).getItem() == card)
            return true;

        NonNullList<ItemStack> inventory = player.inventory.mainInventory;
        for (ItemStack itemStack : inventory)
            if (itemStack.getItem() == card)
                return true;
        return false;
    }

    public static void checkPlayerInventory(ServerPlayerEntity player)
    {
        boolean[] flags = new boolean[10];  //TODO if not used, remove

        Item item = player.inventory.offHandInventory.get(0).getItem();
        if (item instanceof AbilityCard)
            checkCard((AbilityCard) item, player, flags);

        NonNullList<ItemStack> inventory = player.inventory.mainInventory;
        for (ItemStack itemStack : inventory)
        {
            item = itemStack.getItem();
            if (item instanceof AbilityCard)
                checkCard((AbilityCard) item, player, flags);
        }

    }

    private static void checkCard(AbilityCard card, ServerPlayerEntity player, boolean[] flags)
    {
        switch (card.getRegistryName().getPath())
        {
            case "mainshot_pu":
                activateMainShotPu(player);
                return;
            case "speedqueen":
                activateSpeedQueen(player);
                return;
        }
    }

    public static boolean checkAutoBombActivation(ServerPlayerEntity player)
    {
        if (!doPlayerHold(player, ItemReg.AUTOBOMB.get()))
            return false;
        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        PlayerProperties playerProperties = cap.orElse(null);
        if (playerProperties.canSpell(player))
        {
            playerProperties.canSpell(player);
            return true;
        } else return false;
    }

    public static void activateMainShotPu(ServerPlayerEntity player)
    {
        player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 30, 2));
//TODO add attack radius
//            player.getAttributeManager().removeModifiers(AbilityCardUtils.MainShot_PuUtils.getAttributeModifier());
    }

//    public static class MainShot_PuUtils
//    {
//        public static Multimap<Attribute, AttributeModifier> getAttributeModifier()
//        {
//            Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
//            map.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier("reach_distance", 7.0, AttributeModifier.Operation.ADDITION));
//
//            return map;
//        }
//    }

    //TODO MAGICSCROLL not fully finished!!!

    //TODO MAINSHOT_SP unfinished!!!

    public static void activateSpeedQueen(ServerPlayerEntity player)
    {
        if (!player.isSneaking() && (player.inventory.getCurrentItem().isEmpty() || player.inventory.getCurrentItem().getItem() == ItemReg.SPEEDQUEEN.get()))
            player.addPotionEffect(new EffectInstance(Effects.SPEED, 30, 2));
    }

    public static void checkRokumon(LivingDeathEvent event, LivingEntity livingEntity)
    {

    }
}
