package lnatit.mcardsth.item;

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

        if (this.getRegistryName() != null && !GetBoolean(playerIn, path, false))
        {
            SetBoolean(playerIn, path, true);
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
