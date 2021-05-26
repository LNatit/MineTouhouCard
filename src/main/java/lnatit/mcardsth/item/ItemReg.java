package lnatit.mcardsth.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class ItemReg
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);


    public static final RegistryObject<Item> EXTEND = ITEMS.register("extend", instantCard::new);
    public static final RegistryObject<Item> BOMB = ITEMS.register("bomb", instantCard::new);
    public static final RegistryObject<Item> EXTEND2 = ITEMS.register("extend2", instantCard::new);
    public static final RegistryObject<Item> BOMB2 = ITEMS.register("bomb2", instantCard::new);
    public static final RegistryObject<Item> PENDULUM = ITEMS.register("pendulum", instantCard::new);
    public static final RegistryObject<Item> DANGO = ITEMS.register("dango", instantCard::new);
    public static final RegistryObject<Item> MOKOU = ITEMS.register("mokou", instantCard::new);

    public static final RegistryObject<Item> REIMU_OP = ITEMS.register("reimu_op", optionCard::new);
    public static final RegistryObject<Item> REIMU_OP2 = ITEMS.register("reimu_op2", optionCard::new);
    public static final RegistryObject<Item> MARISA_OP = ITEMS.register("marisa_op", optionCard::new);
    public static final RegistryObject<Item> MARISA_OP2 = ITEMS.register("marisa_op2", optionCard::new);
    public static final RegistryObject<Item> SAKUYA_OP = ITEMS.register("sakuya_op", optionCard::new);
    public static final RegistryObject<Item> SAKUYA_OP2 = ITEMS.register("sakuya_op2", optionCard::new);
    public static final RegistryObject<Item> SANAE_OP = ITEMS.register("sanae_op", optionCard::new);
    public static final RegistryObject<Item> SANAE_OP2 = ITEMS.register("sanae_op2", optionCard::new);
    public static final RegistryObject<Item> YOUMU_OP = ITEMS.register("youmu_op", optionCard::new);
    public static final RegistryObject<Item> ALICE_OP = ITEMS.register("alice_op", optionCard::new);
    public static final RegistryObject<Item> CIRNO_OP = ITEMS.register("cirno_op", optionCard::new);
    public static final RegistryObject<Item> OKINA_OP = ITEMS.register("okina_op", optionCard::new);
    public static final RegistryObject<Item> NUE_OP = ITEMS.register("nue_op", optionCard::new);
    public static final RegistryObject<Item> MAGATAMA = ITEMS.register("magatama", optionCard::new);

    public static final RegistryObject<Item> ITEM_CATCH = ITEMS.register("item_catch", abilityCard::new);
    public static final RegistryObject<Item> ITEM_LINE = ITEMS.register("item_line", abilityCard::new);
    public static final RegistryObject<Item> AUTOBOMB = ITEMS.register("autobomb", abilityCard::new);
    public static final RegistryObject<Item> DBOMBEXTD = ITEMS.register("dbombextd", abilityCard::new);
    public static final RegistryObject<Item> MAINSHOT_PU = ITEMS.register("mainshot_pu", abilityCard::new);
    public static final RegistryObject<Item> MAGICSCROLL = ITEMS.register("magicscroll", abilityCard::new);
    public static final RegistryObject<Item> KOISHI = ITEMS.register("koishi", abilityCard::new);
    public static final RegistryObject<Item> MAINSHOT_SP = ITEMS.register("mainshot_sp", abilityCard::new);
    public static final RegistryObject<Item> SPEEDQUEEN = ITEMS.register("speedqueen", abilityCard::new);
    public static final RegistryObject<Item> OPTION_BR = ITEMS.register("option_br", abilityCard::new);
    public static final RegistryObject<Item> DEADSPELL = ITEMS.register("deadspell", abilityCard::new);
    public static final RegistryObject<Item> POWERMAX = ITEMS.register("powermax", abilityCard::new);
    public static final RegistryObject<Item> YOYOKO = ITEMS.register("yoyoko", abilityCard::new);
    public static final RegistryObject<Item> MONEY = ITEMS.register("money", abilityCard::new);
    public static final RegistryObject<Item> ROKUMON = ITEMS.register("rokumon", abilityCard::new);
    public static final RegistryObject<Item> NARUMI = ITEMS.register("narumi", abilityCard::new);
    public static final RegistryObject<Item> PACHE = ITEMS.register("pache", abilityCard::new);
    public static final RegistryObject<Item> MANEKI = ITEMS.register("maneki", abilityCard::new);
    public static final RegistryObject<Item> YAMAWARO = ITEMS.register("yamawaro", abilityCard::new);
    public static final RegistryObject<Item> KISERU = ITEMS.register("kiseru", abilityCard::new);
    public static final RegistryObject<Item> MUKADE = ITEMS.register("mukade", abilityCard::new);

    public static final RegistryObject<Item> WARP = ITEMS.register("warp", usableCard::new);
    public static final RegistryObject<Item> KOZUCHI = ITEMS.register("kozuchi", usableCard::new);
    public static final RegistryObject<Item> KANAME = ITEMS.register("kaname", usableCard::new);
    public static final RegistryObject<Item> MOON = ITEMS.register("moon", usableCard::new);
    public static final RegistryObject<Item> MIKOFLASH = ITEMS.register("mikoflash", usableCard::new);
    public static final RegistryObject<Item> VAMPIRE = ITEMS.register("vampire", usableCard::new);
    public static final RegistryObject<Item> SUN = ITEMS.register("sun", usableCard::new);
    public static final RegistryObject<Item> LILY = ITEMS.register("lily", usableCard::new);
    public static final RegistryObject<Item> BASSDRUM = ITEMS.register("bassdrum", usableCard::new);
    public static final RegistryObject<Item> PSYCO = ITEMS.register("psyco", usableCard::new);
    public static final RegistryObject<Item> CYLINDER = ITEMS.register("cylinder", usableCard::new);
    public static final RegistryObject<Item> RICEBALL = ITEMS.register("riceball", usableCard::new);

    public static final RegistryObject<Item> BLANK = ITEMS.register("blank", attributeCard::new);
    public static final RegistryObject<Item> MAGATAMA2 = ITEMS.register("magatama2", attributeCard::new);

}