package com.shedhack.logging.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.shedhack.logging.annotation.Loggable;

/**
 * This implementation is responsible for all high-level logging.
 */
@Component
@Aspect
public class LoggingAspect
{
    /**
     * Instantiates a new default logging aspect
     */
    public LoggingAspect()
    {
    }

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(loggable)")
    public Object logAround(ProceedingJoinPoint pjp, Loggable loggable) throws Throwable
    {
        try
        {
            logBefore(pjp, loggable);
            Object object = pjp.proceed();
            logAfterResponse(pjp, object, loggable);
            return object;
        }
        catch (Exception ex)
        {
            logException(pjp, ex, loggable);
            throw ex;
        }
    }

    /**
     * @param pjp
     */
    public void logBefore(ProceedingJoinPoint pjp, Loggable loggable)
    {
        if (loggable.logBefore() && !loggable.logOnlyExceptions())
            writeToLog(new StringBuilder().append(" [Before] ").append(buildMethodString(pjp, loggable)).toString(), loggable);
    }

    /**
     * @param pjp
     * @param response
     */
    public void logAfterResponse(ProceedingJoinPoint pjp, Object response, Loggable loggable)
    {
        if (loggable.logResponse() && !loggable.logOnlyExceptions())
        {
            StringBuilder builder = new StringBuilder().append(" [Response] ").append(buildMethodString(pjp, loggable));

            if (loggable.logArgumentsAndResults())
                builder.append(" [Return] ").append(response);

            writeToLog(builder.toString(), loggable);
        }
    }

    /**
     * @param pjp
     * @param ex
     */
    public void logException(ProceedingJoinPoint pjp, Exception ex, Loggable loggable)
    {
        if (loggable.logExceptions() || loggable.logOnlyExceptions())
            logger.error(new StringBuilder().append(" [Exception] ").append(buildMethodString(pjp, loggable)).append(" [Message] ").append(ex.getLocalizedMessage()).append("\n")
                    .append(getStackTrace(ex)).toString());
    }

    /**
     * Builds the method string.
     * 
     * @param joinPoint
     *            the join point
     * @return the string
     */
    private String buildMethodString(ProceedingJoinPoint pjp, Loggable loggable)
    {

        StringBuilder builder = new StringBuilder("[class] ").append(pjp.getTarget().getClass().getSimpleName()).append(" [method] ").append(pjp.getSignature());

        if (loggable.logArgumentsAndResults())
            builder.append(" [args] ").append(Arrays.toString(pjp.getArgs()));

        return builder.toString();
    }

    /**
     * Gets the stack trace.
     * 
     * @param throwable
     *            the throwable
     * @return the stack trace
     */
    private static String getStackTrace(Throwable throwable)
    {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        return writer.toString();
    }

    /**
     * @param msg
     * @param loggable
     */
    private void writeToLog(String msg, Loggable loggable)
    {
        switch (loggable.logLevel())
        {
            case TRACE:
                logger.trace(msg);
                break;

            case DEBUG:
                logger.debug(msg);
                break;

            case WARN:
                logger.warn(msg);
                break;

            default:
                logger.info(msg);
                break;
        }
    }
}
