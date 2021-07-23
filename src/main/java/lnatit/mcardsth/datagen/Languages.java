package lnatit.mcardsth.datagen;

import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class Languages extends LanguageProvider
{
    public Languages(DataGenerator gen, String modid, String locale)
    {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations()
    {
        add("item.minecardstouhou.unknown", "");
        for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
            addItem(itemObj, "");
        for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
        {
            if (itemObj.get() instanceof AbstractCard)
            {
                add("advancement." + MOD_ID + '.' + itemObj.get().getRegistryName().getPath() + ".title", "");
                add("advancement." + MOD_ID + '.' + itemObj.get().getRegistryName().getPath() + ".description", "");
            }
        }
        for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
            for (int i = 0; i < 4; i++)
                add("desc." + MOD_ID + '.' + itemObj.get().getRegistryName().getPath() + (i + 1), "");

    }
}
