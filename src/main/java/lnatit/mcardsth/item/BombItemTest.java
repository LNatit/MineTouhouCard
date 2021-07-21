package lnatit.mcardsth.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

//TODO consider to register new types of stats.
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
//        ItemStack itemstack = playerIn.getHeldItem(handIn);
//
//        if (handIn == Hand.MAIN_HAND)
//        {
//            if (BombType.playerBomb(worldIn, playerIn, BombType.DEFAULT))
//            {
//                playerIn.setActiveHand(handIn);
//                return ActionResult.resultConsume(itemstack);
//            } else
//                return ActionResult.resultFail(itemstack);
//        } else return ActionResult.resultPass(itemstack);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
