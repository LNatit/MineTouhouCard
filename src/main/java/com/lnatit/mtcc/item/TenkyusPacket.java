package com.lnatit.mtcc.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class TenkyusPacket extends Item
{
    public static final String NAME = "album";

    public TenkyusPacket()
    {
        super(new Properties().rarity(Rarity.RARE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer)
        {
            // TODO open GUI

            return InteractionResultHolder.consume(stack);
        }

        return InteractionResultHolder.success(stack);
    }
}
