package com.visiansystems.util.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that is used to mark methods for call logging. Annotate a method
 * with this annotation and set the desired logging level as value.
 * <p/>
 * For example:
 *
 * @CallLogging(Level.DEBUG) void foo() {
 * }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CallLogging {

    Level value();

    public enum Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }
}
