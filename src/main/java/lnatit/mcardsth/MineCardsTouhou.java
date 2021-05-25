package lnatit.mcardsth;

import lnatit.mcardsth.item.ItemReg;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("minecardstouhou")
public class MineCardsTouhou
{
    public static final String MOD_ID = "minecardstouhou";

    public static final String MOD_NAME = "Mine[Touhou]Card";

    public MineCardsTouhou()
    {
        ItemReg.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
