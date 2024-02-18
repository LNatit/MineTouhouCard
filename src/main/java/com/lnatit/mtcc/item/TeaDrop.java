package com.lnatit.mtcc.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import static com.lnatit.mtcc.MTCC.MOD_ID;

public class TeaDrop extends Item
{
    public static final String KEY = MOD_ID + ".imt";

    public TeaDrop(DropTier tier)
    {
        super(new Properties().rarity(tier.getRarity()));
        tier.item = this;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        return super.use(level, player, hand);
    }

    public static ItemStack imitateTo(ItemStack input)
    {
        return imitateTo(input, DropTier.COMMON);
    }

    public static ItemStack imitateTo(ItemStack input, DropTier tier)
    {
        ResourceLocation rl = ForgeRegistries.ITEMS.getKey(input.getItem());
        if (rl == null)
            return ItemStack.EMPTY;
        ItemStack stack = new ItemStack(tier.getItem(), 1);
        CompoundTag itemNBT = input.getOrCreateTag();
        CompoundTag modNBT = new CompoundTag();
        modNBT.putString("target", rl.toString());
        itemNBT.put(MOD_ID, modNBT);
        stack.setTag(itemNBT);
        return stack;
    }

    public static Item imitateFromItem(ItemStack pStack)
    {
        String rlStr = pStack.getOrCreateTag().getCompound(MOD_ID).getString("target");
        ResourceLocation rl = new ResourceLocation(rlStr);
        return ForgeRegistries.ITEMS.getValue(rl);
    }

    public static ItemStack imitateFromStack(ItemStack pStack)
    {
        Item targetItem = imitateFromItem(pStack);
        if (targetItem == null || targetItem == Items.AIR)
            return pStack;
//        pStack.getOrCreateTag().remove(MOD_ID);
        ItemStack target = new ItemStack(targetItem, 1);
        target.setTag(pStack.getTag());
        return target;
    }

    public enum DropTier
    {
        COMMON(Rarity.COMMON),
        UNCOMMON(Rarity.UNCOMMON),
        RARE(Rarity.RARE),
        EPIC(Rarity.EPIC);

        private final Rarity rarity;
        private Item item;

        DropTier(Rarity rarity)
        {
            this.rarity = rarity;
        }

        public Rarity getRarity()
        {
            return rarity;
        }

        public Item getItem()
        {
            return item;
        }

        public static DropTier from(int ordinal)
        {
            if (ordinal >= 4 || ordinal < 0)
                return COMMON;
            return values()[ordinal];
        }
    }
}
