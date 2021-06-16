package lnatit.mcardsth.item;

import lnatit.mcardsth.capability.PlayerProperties;
import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import lnatit.mcardsth.network.CardActivationPacket;
import lnatit.mcardsth.network.NetworkManager;
import lnatit.mcardsth.utils.BombType;
import lnatit.mcardsth.utils.InstantCardUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class BombItemTest extends Item
{
    public BombItemTest()
    {
        super(new Item.Properties().group(CardGroup.CARDS).maxStackSize(1).rarity(Rarity.RARE));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (handIn == Hand.MAIN_HAND)
        {
            if (BombType.playerBomb(worldIn, playerIn, BombType.DEFAULT))
            {
                playerIn.addStat(Stats.ITEM_USED.get(this), 1);

                playerIn.setActiveHand(handIn);
                return ActionResult.resultConsume(itemstack);
            } else
                return ActionResult.resultFail(itemstack);
        } else return ActionResult.resultPass(itemstack);
    }
}
