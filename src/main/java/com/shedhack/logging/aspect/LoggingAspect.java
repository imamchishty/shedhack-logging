package com.shedhack.logging.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

    @SuppressWarnings("unused")
    @Pointcut("@annotation(com.shedhack.logging.annotation.Loggable)")
    private void packagePointcut()
    {
    };

    @Around("packagePointcut()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable
    {
        try
        {
            logBefore(pjp);
            Object object = pjp.proceed();
            logAfterResponse(pjp, object);
            return object;
        }
        catch (Exception ex)
        {
            logException(pjp, ex);
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void logBefore(ProceedingJoinPoint pjp)
    {
        writeToLog(new StringBuilder().append(" [Before] ").append(buildMethodString(pjp)).toString(), false);
    }

    /**
     * {@inheritDoc}
     */
    public void logAfterResponse(ProceedingJoinPoint pjp, Object response)
    {
        writeToLog(new StringBuilder().append(" [Response] ").append(buildMethodString(pjp)).append(" [Return] ").append(response).toString(), false);
    }

    /**
     * {@inheritDoc}
     */
    public void logException(ProceedingJoinPoint pjp, Exception ex)
    {
        writeToLog(new StringBuilder().append(" [Exception] ").append(buildMethodString(pjp)).append(" [Message] ").append(ex.getLocalizedMessage()).append("\n").append(getStackTrace(ex)).toString(),
                true);
    }

    /**
     * Builds the method string.
     * 
     * @param joinPoint
     *            the join point
     * @return the string
     */
    private String buildMethodString(ProceedingJoinPoint pjp)
    {
        return new StringBuilder("[class] ").append(pjp.getTarget().getClass().getSimpleName()).append(" [method] ").append(pjp.getSignature()).append(" [args] ")
                .append(Arrays.toString(pjp.getArgs())).toString();
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
     * Write to log.
     * 
     * @param msg
     *            the msg
     * @param exception
     *            the exception
     */
    private void writeToLog(String msg, boolean exception)
    {
        if (exception)
        {
            logger.error(msg);
        }
        else
        {
            logger.info(msg);
        }
    }
}
