package lnatit.mcardsth.item;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class LifeItem extends Item
{
    public LifeItem()
    {
        super(new Item.Properties().maxStackSize(1).rarity(Rarity.RARE));
    }
}
