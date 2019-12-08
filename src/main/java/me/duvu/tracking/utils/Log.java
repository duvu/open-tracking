package me.duvu.tracking.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author beou on 10/2/18 20:04
 */
public class Log {
    private static final Logger logger;

    static {
        logger = LoggerFactory.getLogger("V5Tracking");
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable th) {
        logger.error(message, th);
    }
}
