package com.mock.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mock.test.Foo;


import static org.mockito.Matchers.anyLong;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {Foo.class} )

/**
 * Why this test cannot succeed in my project<BR/>
 */
public class FooTest {
    private Foo foo;
    
    public FooTest() {
        super();
    }
    
    @BeforeClass()
    public static void before() {
    }
    
    @Before
    public void setUp() {
        foo = new Foo();
    }
    
    @Test
    public void testIncrement() throws Exception {
        mockStatic(Thread.class);
        when(Thread.activeCount()).thenReturn(2);

        foo.increment();
        Assert.assertEquals(foo.getI(), 100);
    }

}
