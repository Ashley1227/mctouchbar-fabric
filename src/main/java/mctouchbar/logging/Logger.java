package mctouchbar.logging;

import org.apache.logging.log4j.LogManager;

public class Logger {
    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    public static void info(String s) {
        logger.info("[MCTouchbar] " + s);
    }
    public static void heading (String s, String r) {
        bar(r);
        logger.info("\t" + s);
        bar(r);
    }
    public static String bar(String r) {
        StringBuilder b = new StringBuilder();
        while(b.length() < 50) {
            b.append(r);
        }
        String ret = b.toString();
        logger.info("[MCTouchbar]" + ret);
        return ret;
    }
    public static void error(String s) {
        logger.error("[MCTouchbar] " + s);
    }
    public static void fatal(String s) {
        logger.fatal("[MCTouchbar] " + s);
    }
    public static void debug(String s) {
        logger.debug("[MCTouchbar] " + s);
    }
}
