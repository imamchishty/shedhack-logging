package com.shedhack.test.service;


/**
 * The Interface AdditionService.
 */
public interface MathService
{

    /**
     * Adds two numbers together.
     * 
     * @param a
     *            the a
     * @param b
     *            the b
     * @return the int
     */
    int add(int a, int b);

    /**
     * Adds the upto 100. If the addded value is greater than 100 then we throw
     * an exception.
     * 
     * @param a
     *            the a
     * @param b
     *            the b
     * @return the int
     */
    int addUpto100(int a, int b);
}
