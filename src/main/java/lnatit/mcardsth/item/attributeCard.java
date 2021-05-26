package lnatit.mcardsth.item;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class attributeCard extends Item
{

    public attributeCard()
    {
        super(new Item.Properties().group(CardGroup.CARDS).maxStackSize(1).rarity(Rarity.EPIC));
    }
}
