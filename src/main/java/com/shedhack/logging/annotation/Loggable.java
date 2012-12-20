package com.shedhack.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shedhack.logging.enums.LoggingLevel;

/**
 * Public methods that require logging via aop should use this annotation. In
 * order to use this you must import <code>shedhack-logging-context.xml</code>
 * and mark the public method with this annotation.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable
{
    /**
     * Set to true to see the parameters in the output log. By default they will
     * be enabled. If you have lots of params then this will not be suitable and
     * may cause a reduction in performance. Use with caution.
     */
    boolean logParameters() default true;

    /**
     * If you have a method with a return type then this will be logged.
     */
    boolean logResponse() default true;

    /**
     * Define the logging level
     */
    LoggingLevel logLevel() default LoggingLevel.INFO;
}
