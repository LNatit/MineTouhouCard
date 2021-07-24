package lnatit.mcardsth.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen
{
    @SubscribeEvent
    public static void onDataGen(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(new Languages(generator, MOD_ID, "zh_cn"));
        generator.addProvider(new ItemModels(generator, MOD_ID, helper));
        generator.addProvider(new Advancements(generator));
    }
}
