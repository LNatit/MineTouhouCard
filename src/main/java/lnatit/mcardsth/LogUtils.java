package lnatit.mcardsth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;


public class LogUtils
{
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final boolean SHOW_WARN = true;

    public static void Warn(String str)
    {
        if (SHOW_WARN)
            LOGGER.warn(str);
    }

    public static void Warn(String str, Object...args)
    {
        if (SHOW_WARN)
            LOGGER.warn(String.format(str, args));
    }

    public static void Log(String str)
    {
        LOGGER.info(str);
    }

    public static void Log(String str, Object...args)
    {
            LOGGER.info(String.format(str, args));
    }
}
