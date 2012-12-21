package com.shedhack.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shedhack.logging.aspect.LoggingAspect;
import com.shedhack.logging.enums.LoggingLevel;

/**
 * Public methods that require logging via aop should use this annotation. This
 * annotation is tied closely to the {@link LoggingAspect}. This aspect is
 * marked with {@link org.aspectj.lang.annotation.Aspect} and
 * {@link org.springframework.stereotype.Component}. In order to use this you
 * must all 'com.shedhack.logging' to your component scan and add the aop tag:
 * 
 * <pre>
 *  <code>
 *  <context:component-scan base-package="com.shedhack.logging, com.shedhack.test"/>
 *  <aop:aspectj-autoproxy/>
 *  </code>
 * </pre>
 * 
 * The appropriate logging configuration should also be made available in the
 * classpath. For example:
 * 
 * <pre>
 *  <code>
 *      <?xml version="1.0" encoding="UTF-8"?>
 *      <configuration>
 *          <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
 *              <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
 *                  <pattern>%d %5p | %t | %-55logger{55} | %m %n</pattern>
 *              </encoder>
 *          </appender>
 *              
 *          <logger name="com.shedhack" level="ALL" />
 *              
 *          <root level="INFO">
 *              <appender-ref ref="STDOUT" />
 *          </root>
 *      </configuration>
 *  </code>
 * </pre>
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable
{
    /**
     * Before a method is called, the aspect will log the invocation. You can
     * disable this by setting this property to false. If you're only interested
     * in logging exceptions then it is recommended to set logOnlyExceptions()
     * to true. By doing so you'll automatically stop before and response
     * logging.
     */
    boolean logBefore() default true;

    /**
     * After a method is called, the aspect will log the invocation. You can
     * disable this by setting this property to false. If you're only interested
     * in logging exceptions then it is recommended to set logOnlyExceptions()
     * to true. By doing so you'll automatically stop before and response
     * logging.
     */
    boolean logResponse() default true;

    /**
     * By default all exceptions will be logged. You can disable this if
     * required.
     */
    boolean logExceptions() default true;

    /**
     * By default before, after and exceptions are logged. If you wish to lessen
     * you're logs switch this property to true. By doing so only exceptions
     * will be logged.
     */
    boolean logOnlyExceptions() default false;

    /**
     * In some cases it may be inappropriate to log arguments and results, for
     * example login. In order to prevent the arguments from showing in the logs
     * set this to false. Default is true, which shows the arguments and the
     * results.
     */
    boolean logArgumentsAndResults() default true;

    /**
     * Define the logging level
     */
    LoggingLevel logLevel() default LoggingLevel.INFO;

}
