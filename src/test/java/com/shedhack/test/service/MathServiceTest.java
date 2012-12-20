package com.shedhack.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/logging-test-context.xml" })
public class MathServiceTest
{
    @Autowired MathService service;

    @Test
    public void testAddition()
    {
        assertEquals(22, service.add(10, 12));
    }

    @Test
    public void testAdditionUpto100()
    {
        assertEquals(22, service.addUpto100(10, 12));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdditionException()
    {
        service.addUpto100(100, 12);
    }
}
