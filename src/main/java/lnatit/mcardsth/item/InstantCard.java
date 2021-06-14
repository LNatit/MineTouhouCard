package lnatit.mcardsth.item;

import lnatit.mcardsth.network.CardActivationPacket;
import lnatit.mcardsth.network.NetworkManager;
import lnatit.mcardsth.utils.InstantCardUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (handIn == Hand.MAIN_HAND)
        {
            if (InstantCardUtils.instantCardHandler(playerIn, this))
            {
                playerIn.addStat(Stats.ITEM_USED.get(this), 1);

                if (!worldIn.isRemote)
                    NetworkManager.serverSendToPlayer(new CardActivationPacket(itemstack.getItem()), (ServerPlayerEntity) playerIn);

                playerIn.setActiveHand(handIn);
                itemstack.shrink(1);
                playerIn.getCooldownTracker().setCooldown(this, 20);
                return ActionResult.resultConsume(itemstack);
            } else
            return ActionResult.resultFail(itemstack);
        } else return ActionResult.resultPass(itemstack);
    }


}
