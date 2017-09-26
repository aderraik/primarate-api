package com.visiansystems.util.logger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Aspect that implements logging for {@code CallLogging} annotated methods.
 */
@Aspect
@Component
public class LoggingAspect {
    /**
     * List of loggers.
     */

    /**
     * Entry logging.
     *
     * @param joinPoint   The join point for the method.
     * @param callLogging The call logging annotation.
     */
    @Before(value = "@annotation(callLogging)", argNames = "joinPoint, callLogging")
    public void logEntry(final JoinPoint joinPoint, final CallLogging callLogging) {
        Logger log = LoggerUtils.getLogger(joinPoint);
        Level level = Level.toLevel(callLogging.value().toString());

        if (log.isEnabledFor(level)) {
            log.log(level, "Entering method "
                           + joinPoint.getSignature().getName()
                           + "["
                           + LoggerUtils.getArgs(joinPoint.getArgs())
                           + "]");
        }
    }

    /**
     * Return logging.
     *
     * @param joinPoint   The join point for the method.
     * @param callLogging The call logging annotation.
     * @param returnValue The return value of the called method.
     */
    @AfterReturning(value = "@annotation(returnLogging)",
            argNames = "joinPoint, returnLogging, returnValue", returning = "returnValue")
    public void logExitAfterReturn(final JoinPoint joinPoint,
                                   ReturnLogging callLogging, Object returnValue) {
        Logger log = LoggerUtils.getLogger(joinPoint);
        Level level = Level.toLevel(callLogging.value().toString());

        if (log.isEnabledFor(level)) {
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            Class<?> returnType = signature.getReturnType();
            if (returnType.getName().equals("void")) {
                log.log(level, "Exiting method "
                               + joinPoint.getSignature().getName()
                               + "[void]");
            }
            else {
                log.log(level, "Exiting method "
                               + joinPoint.getSignature().getName()
                               + "[" + returnValue + "]");
            }
        }
    }

    /**
     * Throw logging.
     *
     * @param joinPoint   The join point for the method.
     * @param callLogging The call logging annotation.
     * @param throwable   The return value of the called method.
     */
    @AfterThrowing(value = "@annotation(callLogging)",
            argNames = "joinPoint, callLogging, throwable", throwing = "throwable")
    public void logExitAfterThrowing(final JoinPoint joinPoint,
                                     CallLogging callLogging, Throwable throwable) {
        Logger log = LoggerUtils.getLogger(joinPoint);
        Level level = Level.toLevel(callLogging.value().toString());

        if (log.isEnabledFor(level)) {
            log.log(level, "Exiting method "
                           + joinPoint.getSignature().getName()
                           + "[" + throwable.getCause() + "]");
        }
    }
}
