package lnatit.mcardsth.item;

import lnatit.mcardsth.utils.InstantCardUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class InstantCard extends AbstractCard
{
    public InstantCard()
    {
        super(new Item.Properties().group(CardGroup.CARDS).maxStackSize(1).rarity(Rarity.RARE));
    }

    /**
     * TODO send packet to play animation
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
//        if (worldIn.isRemote)
//            Minecraft.getInstance().gameRenderer.displayItemActivation(playerIn.getHeldItem(Hand.MAIN_HAND));
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (handIn == Hand.MAIN_HAND)
        {
            if (InstantCardUtils.instantCardHandler(playerIn, this))
            {
                playerIn.addStat(Stats.ITEM_USED.get(this), 1);

                if (playerIn instanceof ClientPlayerEntity)
                    Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(itemstack.getItem()));  //TODO

//                worldIn.setEntityState(playerIn, (byte)35);
                playerIn.setActiveHand(handIn);
                itemstack.shrink(1);
                playerIn.getCooldownTracker().setCooldown(this, 20);
                return ActionResult.resultConsume(itemstack);
            } else
            return ActionResult.resultFail(itemstack);
        } else return ActionResult.resultPass(itemstack);
    }


}
