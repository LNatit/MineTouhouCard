package lnatit.mcardsth.item;

import lnatit.mcardsth.utils.InstantCardUtils;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class InstantCard extends AbstractCard
{
    public InstantCard()
    {
        super(new Item.Properties().group(CardGroup.CARDS).maxStackSize(1).rarity(Rarity.RARE));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (handIn == Hand.MAIN_HAND)
        {
            return InstantCardUtils.instantCardHandler(playerIn, (InstantCard) playerIn.getHeldItem(handIn).getItem()) ? ActionResult.resultConsume(playerIn.getHeldItem(handIn)) : ActionResult.resultFail(playerIn.getHeldItem(handIn));
        } else
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }


}
