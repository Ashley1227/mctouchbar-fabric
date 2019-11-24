package mctouchbar.logging;

import org.apache.logging.log4j.LogManager;

public class Logger {
    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    public static void info(String s) {
        logger.info("[MCTouchbar] " + s);
    }
    public static  void error(String s) {
        logger.error("[MCTouchbar] " + s);
    }
    public static  void fatal(String s) {
        logger.fatal("[MCTouchbar] " + s);
    }
    public static void debug(String s) {
        logger.debug("[MCTouchbar] " + s);
    }
}
