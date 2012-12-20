package com.shedhack.test.service;

import org.springframework.stereotype.Service;

import com.shedhack.logging.annotation.Loggable;

/**
 * This Math Service is only for testing. Shows how the {@link Loggable} AOP
 * works.
 */
@Service
public class DefaultMathService implements MathService
{
    /**
     * {@inheritDoc}
     */
    @Loggable
    public int add(int a, int b)
    {
        return a + b;
    }

    /**
     * {@inheritDoc}
     */
    @Loggable
    public int addUpto100(int a, int b)
    {
        if (a + b > 100)
            throw new IllegalArgumentException();
        return a + b;
    }

}
