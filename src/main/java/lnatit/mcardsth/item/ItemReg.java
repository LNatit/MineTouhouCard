package lnatit.mcardsth.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class ItemReg
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);


    public static final RegistryObject<Item> EXTEND = ITEMS.register("extend", InstantCard::new);
    public static final RegistryObject<Item> BOMB = ITEMS.register("bomb", InstantCard::new);
    public static final RegistryObject<Item> EXTEND2 = ITEMS.register("extend2", InstantCard::new);
    public static final RegistryObject<Item> BOMB2 = ITEMS.register("bomb2", InstantCard::new);
    public static final RegistryObject<Item> PENDULUM = ITEMS.register("pendulum", InstantCard::new);
    public static final RegistryObject<Item> DANGO = ITEMS.register("dango", InstantCard::new);
    public static final RegistryObject<Item> MOKOU = ITEMS.register("mokou", InstantCard::new);

    public static final RegistryObject<Item> REIMU_OP = ITEMS.register("reimu_op", OptionCard::new);
    public static final RegistryObject<Item> REIMU_OP2 = ITEMS.register("reimu_op2", OptionCard::new);
    public static final RegistryObject<Item> MARISA_OP = ITEMS.register("marisa_op", OptionCard::new);
    public static final RegistryObject<Item> MARISA_OP2 = ITEMS.register("marisa_op2", OptionCard::new);
    public static final RegistryObject<Item> SAKUYA_OP = ITEMS.register("sakuya_op", OptionCard::new);
    public static final RegistryObject<Item> SAKUYA_OP2 = ITEMS.register("sakuya_op2", OptionCard::new);
    public static final RegistryObject<Item> SANAE_OP = ITEMS.register("sanae_op", OptionCard::new);
    public static final RegistryObject<Item> SANAE_OP2 = ITEMS.register("sanae_op2", OptionCard::new);
    public static final RegistryObject<Item> YOUMU_OP = ITEMS.register("youmu_op", OptionCard::new);
    public static final RegistryObject<Item> ALICE_OP = ITEMS.register("alice_op", OptionCard::new);
    public static final RegistryObject<Item> CIRNO_OP = ITEMS.register("cirno_op", OptionCard::new);
    public static final RegistryObject<Item> OKINA_OP = ITEMS.register("okina_op", OptionCard::new);
    public static final RegistryObject<Item> NUE_OP = ITEMS.register("nue_op", OptionCard::new);
    public static final RegistryObject<Item> MAGATAMA = ITEMS.register("magatama", OptionCard::new);

    public static final RegistryObject<Item> ITEM_CATCH = ITEMS.register("item_catch", AbilityCard::new);
    public static final RegistryObject<Item> ITEM_LINE = ITEMS.register("item_line", AbilityCard::new);
    public static final RegistryObject<Item> AUTOBOMB = ITEMS.register("autobomb", AbilityCard::new);
    public static final RegistryObject<Item> DBOMBEXTD = ITEMS.register("dbombextd", AbilityCard::new);
    public static final RegistryObject<Item> MAINSHOT_PU = ITEMS.register("mainshot_pu", AbilityCard::new);
    public static final RegistryObject<Item> MAGICSCROLL = ITEMS.register("magicscroll", AbilityCard::new);
    public static final RegistryObject<Item> KOISHI = ITEMS.register("koishi", AbilityCard::new);
    public static final RegistryObject<Item> MAINSHOT_SP = ITEMS.register("mainshot_sp", AbilityCard::new);
    public static final RegistryObject<Item> SPEEDQUEEN = ITEMS.register("speedqueen", AbilityCard::new);
    public static final RegistryObject<Item> OPTION_BR = ITEMS.register("option_br", AbilityCard::new);
    public static final RegistryObject<Item> DEADSPELL = ITEMS.register("deadspell", AbilityCard::new);
    public static final RegistryObject<Item> POWERMAX = ITEMS.register("powermax", AbilityCard::new);
    public static final RegistryObject<Item> YOYOKO = ITEMS.register("yoyoko", AbilityCard::new);
    public static final RegistryObject<Item> MONEY = ITEMS.register("money", AbilityCard::new);
    public static final RegistryObject<Item> ROKUMON = ITEMS.register("rokumon", AbilityCard::new);
    public static final RegistryObject<Item> NARUMI = ITEMS.register("narumi", AbilityCard::new);
    public static final RegistryObject<Item> PACHE = ITEMS.register("pache", AbilityCard::new);
    public static final RegistryObject<Item> MANEKI = ITEMS.register("maneki", AbilityCard::new);
    public static final RegistryObject<Item> YAMAWARO = ITEMS.register("yamawaro", AbilityCard::new);
    public static final RegistryObject<Item> KISERU = ITEMS.register("kiseru", AbilityCard::new);
    public static final RegistryObject<Item> MUKADE = ITEMS.register("mukade", AbilityCard::new);

    public static final RegistryObject<Item> WARP = ITEMS.register("warp", UsableCard::new);
    public static final RegistryObject<Item> KOZUCHI = ITEMS.register("kozuchi", UsableCard::new);
    public static final RegistryObject<Item> KANAME = ITEMS.register("kaname", UsableCard::new);
    public static final RegistryObject<Item> MOON = ITEMS.register("moon", UsableCard::new);
    public static final RegistryObject<Item> MIKOFLASH = ITEMS.register("mikoflash", UsableCard::new);
    public static final RegistryObject<Item> VAMPIRE = ITEMS.register("vampire", UsableCard::new);
    public static final RegistryObject<Item> SUN = ITEMS.register("sun", UsableCard::new);
    public static final RegistryObject<Item> LILY = ITEMS.register("lily", UsableCard::new);
    public static final RegistryObject<Item> BASSDRUM = ITEMS.register("bassdrum", UsableCard::new);
    public static final RegistryObject<Item> PSYCO = ITEMS.register("psyco", UsableCard::new);
    public static final RegistryObject<Item> CYLINDER = ITEMS.register("cylinder", UsableCard::new);
    public static final RegistryObject<Item> RICEBALL = ITEMS.register("riceball", UsableCard::new);

    public static final RegistryObject<Item> BLANK = ITEMS.register("blank", AttributeCard::new);
    public static final RegistryObject<Item> MAGATAMA2 = ITEMS.register("magatama2", AttributeCard::new);

    public static final RegistryObject<Item> ABS_LIFE = ITEMS.register("abstract_life", LifeItemTest::new);
    public static final RegistryObject<Item> ABS_BOMB = ITEMS.register("abstract_bomb", BombItemTest::new);

}