package lnatit.mcardsth.item;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

//TODO consider to register new types of stats.
public class LifeItemTest extends Item
{
    public LifeItemTest()
    {
        super(new Item.Properties().maxStackSize(1).rarity(Rarity.RARE));
    }
}
