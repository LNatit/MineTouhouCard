package lnatit.mcardsth.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class optionCard extends AbstractCard
{
    public optionCard()
    {
        super(new Item.Properties().group(CardGroup.CARDS).maxStackSize(1).rarity(Rarity.RARE));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        return null;
    }
}
