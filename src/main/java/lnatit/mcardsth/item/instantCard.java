package lnatit.mcardsth.item;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class instantCard extends Item
{
    public instantCard()
    {
        super(new Item.Properties().group(CardGroup.CARDS).maxStackSize(1).rarity(Rarity.RARE));
    }
}
