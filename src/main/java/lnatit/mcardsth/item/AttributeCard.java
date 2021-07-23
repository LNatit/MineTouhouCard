package lnatit.mcardsth.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AttributeCard extends AbstractCard
{
    public AttributeCard()
    {
        super(new Item.Properties()
//                .group(CardGroup.CARDS)
                      .maxStackSize(1)
                      .rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (cardCollection(playerIn) && this == ItemReg.BLANK.get())
        {
            ItemStack itemStack1 = new ItemStack(ItemReg.TENKYU_S_PACKET.get());
            if (playerIn instanceof ServerPlayerEntity)
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerIn, itemstack);
            return ActionResult.func_233538_a_(itemStack1, worldIn.isRemote());
        }
        else return ActionResult.resultFail(itemstack);
    }
}
