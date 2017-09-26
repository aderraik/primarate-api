package com.visiansystems.util.logger;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import java.util.HashMap;
import java.util.Map;

public class LoggerUtils {

    private static final Map<Class<?>, Logger> loggers = new HashMap<Class<?>, Logger>();

    /**
     * Creates a string presentation of method arguments.
     *
     * @param args The array of arguments.
     * @return A new string constructed from the arguments.
     */
    public static String getArgs(Object... args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            sb.append(args[i]);
            if (i + 1 < args.length) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    /**
     * Returns a logger for the join point. Gets a logger for the class of the
     * called method.
     *
     * @param joinPoint The join point for the method.
     * @return The logger.
     */
    public static Logger getLogger(final JoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getTarget().getClass();
        Logger log = loggers.get(clazz);
        if (log == null) {
            log = Logger.getLogger(clazz);
            loggers.put(clazz, log);
        }

        return log;
    }
}
