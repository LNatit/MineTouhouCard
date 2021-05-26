package lnatit.mcardsth.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static lnatit.mcardsth.MineCardsTouhou.MOD_NAME;

public class CardGroup extends ItemGroup
{
    public static final ItemGroup CARDS = new CardGroup();

    public CardGroup()
    {
        super(MOD_NAME);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack createIcon()
    {
        return new ItemStack(ItemReg.EXTEND.get());
    }
}
