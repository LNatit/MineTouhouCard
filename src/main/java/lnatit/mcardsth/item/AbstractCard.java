package lnatit.mcardsth.item;

import lnatit.mcardsth.utils.PlayerPropertiesUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

import static deeplake.idlframework.idlnbtutils.IDLNBT.*;
import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;
import static lnatit.mcardsth.utils.PlayerPropertiesUtils.doPlayerCollected;
import static net.minecraft.item.Items.DEBUG_STICK;

public class AbstractCard extends Item
{
    public static final String UNKNOWN = "item." + MOD_ID + ".unknown";

    public AbstractCard(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.getHeldItem(Hand.OFF_HAND).getItem() == DEBUG_STICK && cardCollection(playerIn, itemstack))
        {
            itemstack.shrink(1);
            return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
        }
        else return ActionResult.resultFail(itemstack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.CROSSBOW;
    }

    protected boolean cardCollection(PlayerEntity playerIn, ItemStack stack)
    {
        if (!doPlayerCollected(playerIn, this))
        {
            PlayerPropertiesUtils.collectCard(playerIn, this);
            if (playerIn instanceof ServerPlayerEntity)
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerIn, stack);
            return true;
        }
        else return false;
    }

    @Override
    public String getTranslationKey()
    {
        if (this instanceof EasterCard)
            return UNKNOWN;
        if (FMLEnvironment.dist == Dist.CLIENT && !doPlayerCollected(
                (PlayerEntity) (Object) Minecraft.getInstance().player, this))
            return UNKNOWN;
        return super.getTranslationKey();
    }
}
