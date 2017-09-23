package com.visiansystems.util.logger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * TODO: Criar toogle dos logger especificos.
 */
@Aspect
@Component
public class BcbFeedLoggingAspect {

    @Around("execution(public * com.visiansystems.bl.bankRateFeed.bcb.BcbRateFeed.update(..))")
    public void UpdateAdvice(ProceedingJoinPoint joinPoint) {
        Logger log = LoggerUtils.getLogger(joinPoint);

        try {

            log.log(Level.INFO, "Running method " +
                                joinPoint.getSignature().getName() + "(" +
                                LoggerUtils.getArgs(joinPoint.getArgs()) + ") ...");

            joinPoint.proceed();
            log.log(Level.INFO, "Ended method " + joinPoint.getSignature().getName());
        }
        catch (Throwable throwable) {
            log.log(Level.ERROR, throwable.getMessage());
        }
    }
}
