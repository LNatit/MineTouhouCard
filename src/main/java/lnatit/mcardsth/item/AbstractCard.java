package lnatit.mcardsth.item;

import lnatit.mcardsth.utils.PlayerPropertiesUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import static deeplake.idlframework.idlnbtutils.IDLNBTUtils.*;

public class AbstractCard extends Item
{
    public AbstractCard(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        String path = this.getRegistryName().getPath();

        if (!PlayerPropertiesUtils.doPlayerCollected(playerIn, this))
        {
            PlayerPropertiesUtils.collectCard(playerIn, this);
            //TODO add advancements
            itemstack.setCount(0);
            return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
        }
        else return ActionResult.resultFail(itemstack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.CROSSBOW;
    }
}
