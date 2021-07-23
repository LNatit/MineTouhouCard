package lnatit.mcardsth.datagen;

import lnatit.mcardsth.item.ItemReg;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

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
        {
            addItem(itemObj, "");
//            add();
        }
    }
}
