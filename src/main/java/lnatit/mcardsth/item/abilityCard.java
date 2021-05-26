package lnatit.mcardsth.item;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class abilityCard extends Item
{
    public abilityCard()
    {
        super(new Item.Properties().group(CardGroup.CARDS).maxStackSize(1).rarity(Rarity.RARE));
    }
}
