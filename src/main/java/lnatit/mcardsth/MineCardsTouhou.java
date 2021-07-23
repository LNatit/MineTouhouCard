package lnatit.mcardsth;

import lnatit.mcardsth.entity.EntityTypeReg;
import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.utils.Config;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod(MOD_ID)
public class MineCardsTouhou
{
    public static final String MOD_ID = "minecardstouhou";

    public static final String MOD_NAME = "Mine[Touhou]Card";

    public MineCardsTouhou()
    {
        ItemReg.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        EntityTypeReg.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.init());
    }
}
