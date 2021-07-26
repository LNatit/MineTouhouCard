package lnatit.mcardsth.item;

import lnatit.mcardsth.utils.AdvancementUtils;
import net.minecraft.entity.player.PlayerEntity;
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
                      .group(CardGroup.CARDS)
                      .maxStackSize(1)
                      .rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (this == ItemReg.BLANK.get())
        {
            if (cardCollection(playerIn, itemstack))
            {
                ItemStack itemStack1 = new ItemStack(ItemReg.TENKYU_S_PACKET.get());
                return ActionResult.func_233538_a_(itemStack1, worldIn.isRemote());
            }
            else
            {
                AdvancementUtils.giveAdvancement(playerIn, "tenkyus_packet");
                return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
            }
        }
        else return ActionResult.resultFail(itemstack);
    }
}
