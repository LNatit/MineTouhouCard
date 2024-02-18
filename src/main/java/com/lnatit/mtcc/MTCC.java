package com.lnatit.mtcc;

import com.lnatit.mtcc.item.TeaDrop;
import com.lnatit.mtcc.item.TenkyusPacket;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static com.lnatit.mtcc.MTCC.MOD_ID;

@Mod(MOD_ID)
public class MTCC
{
    public static final String MOD_ID = "mtcc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<Item> ALBUM = ITEMS.register(TenkyusPacket.NAME, TenkyusPacket::new);
    public static final RegistryObject<Item> DROP_0 = ITEMS.register("drop_0", () -> new TeaDrop(TeaDrop.DropTier.COMMON));
    public static final RegistryObject<Item> DROP_1 = ITEMS.register("drop_1", () -> new TeaDrop(TeaDrop.DropTier.UNCOMMON));
    public static final RegistryObject<Item> DROP_2 = ITEMS.register("drop_2", () -> new TeaDrop(TeaDrop.DropTier.RARE));
    public static final RegistryObject<Item> DROP_3 = ITEMS.register("drop_3", () -> new TeaDrop(TeaDrop.DropTier.EPIC));

    public MTCC()
    {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
    }

}
