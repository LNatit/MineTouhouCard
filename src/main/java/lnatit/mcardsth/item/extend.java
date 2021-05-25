package lnatit.mcardsth.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class extend extends Item
{
    public extend()
    {
        super(new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(1).rarity(Rarity.RARE));
    }
}
