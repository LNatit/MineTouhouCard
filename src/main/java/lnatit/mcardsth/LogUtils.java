package lnatit.mcardsth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogUtils
{
    public static final Logger log = LogManager.getLogger();
    public static final boolean SHOW_WARN = true;

    public static void Warn(String str)
    {
        if (SHOW_WARN)
            log.warn(str);
    }

    public static void Warn(String str, Object...args)
    {
        if (SHOW_WARN)
            log.warn(String.format(str, args));
    }

    public static void Log(String str)
    {
        log.info(str);
    }

    public static void Log(String str, Object...args)
    {
            log.info(String.format(str, args));
    }
}
