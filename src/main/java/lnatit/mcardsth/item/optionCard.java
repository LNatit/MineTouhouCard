package lnatit.mcardsth.item;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class optionCard extends Item
{
    public optionCard()
    {
        super(new Item.Properties().group(CardGroup.CARDS).maxStackSize(1).rarity(Rarity.RARE));
    }
}
