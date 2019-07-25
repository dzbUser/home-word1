package com.aiwan.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
public class LoggerUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger("console");

    public static void error(String str, Object... args) {
        LOGGER.error(str, args);
    }

    public static void error(String str) {
        LOGGER.error(str);
    }

    public static void info(String str) {
        LOGGER.info(str);
    }

    public static void info(String str, Object... args) {
        LOGGER.info(str, args);
    }
}
