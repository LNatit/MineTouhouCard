package lnatit.mcardsth.datagen;

import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class ItemModels extends ItemModelProvider
{
    public ItemModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        ResourceLocation location = new ResourceLocation(MOD_ID, "item/unknown");

        for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
        {
            if (itemObj.get() instanceof AbstractCard)
            {
                String name = itemObj.get().getRegistryName().getPath();
                singleTexture(name + "_unlocked",
                              mcLoc("generated"),
                              "layer0",
                              new ResourceLocation(MOD_ID, "item/" + name));
            }
        }

        for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
        {
            String name;

            if (itemObj.get() instanceof AbstractCard)
            {
                name = itemObj.get().getRegistryName().getPath();

                ModelFile model =
                        new ModelFile.ExistingModelFile(new ResourceLocation(MOD_ID, "item/" + name + "_unlocked"), existingFileHelper);

                singleTexture(name, mcLoc("generated"), "layer0", location)
                        .override()
                        .predicate(new ResourceLocation(MOD_ID, name + ".unlocked"), 1)
                        .model(model);
            }

        }
    }
}
