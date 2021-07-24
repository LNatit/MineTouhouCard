package lnatit.mcardsth.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config
{
    public static ForgeConfigSpec.IntValue DEFAULT_LIFE_COUNT;
    public static ForgeConfigSpec.IntValue DEFAULT_LIFE_FRAG;
    public static ForgeConfigSpec.IntValue DEFAULT_BOMB_COUNT;
    public static ForgeConfigSpec.IntValue DEFAULT_BOMB_FRAG;

    public static ForgeConfigSpec.IntValue PENDULUM_AWARD;
    public static ForgeConfigSpec.IntValue ROKUMON_SACRIFICE;
    public static ForgeConfigSpec.BooleanValue ITEM_FRAME_DISPLAY;
    public static ForgeConfigSpec.BooleanValue ARMOR_STAND_DISPLAY;

    public static ForgeConfigSpec init()
    {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("Player Data");
        {
            builder.comment("Default value of player's life:");
            DEFAULT_LIFE_COUNT = builder.defineInRange("defaultLife", 0, 0, 7);

            builder.comment("Default value of player's life fragment:");
            DEFAULT_LIFE_FRAG = builder.defineInRange("defaultLifeFrag", 0, 0, 2);

            builder.comment("Default value of player's bomb:");
            DEFAULT_BOMB_COUNT = builder.defineInRange("defaultBomb", 0, 0, 32767);

            builder.comment("Default value of player's bomb fragment:");
            DEFAULT_BOMB_FRAG = builder.defineInRange("defaultBombFrag", 0, 0, 2);
        }
        builder.pop();

        builder.push("Card Properties");
        {
            builder.comment("Count of the emerald awarded once player uses a pendulum card:");
            PENDULUM_AWARD = builder.defineInRange("pendulumAward", 12, 1, 64);

            builder.comment("Count of the emerald player need to sacrifice encountering death with rokumon in hand:");
            ROKUMON_SACRIFICE = builder.defineInRange("rokumonSacrifice", 12, 1, 64);

            builder.comment("Whether the card renders its unlocked texture when displayed in an Item Frame:");
            ITEM_FRAME_DISPLAY = builder.define("itemFrameDisplay", true);

            builder.comment("Whether the card renders its unlocked texture when displayed on an Armor Stand:");
            ARMOR_STAND_DISPLAY = builder.define("itemFrameDisplay", false);
        }
        builder.pop();

        return builder.build();
    }
}
