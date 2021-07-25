package lnatit.mcardsth.item;

import lnatit.mcardsth.utils.BombType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

//TODO remains to be rewritten
@Deprecated
public class BombItemTest extends Item
{
    public BombItemTest()
    {
        super(new Item.Properties().maxStackSize(1).rarity(Rarity.RARE));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (handIn == Hand.MAIN_HAND)
        {
            if (BombType.playerBomb(worldIn, playerIn, BombType.DEFAULT))
            {
                playerIn.setActiveHand(handIn);
                if (playerIn instanceof ServerPlayerEntity)
                    CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerIn, itemstack);
                return ActionResult.resultConsume(itemstack);
            } else
                return ActionResult.resultFail(itemstack);
        } else return ActionResult.resultPass(itemstack);
    }
}
