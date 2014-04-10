package com.mock.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mock.test.Foo;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Foo.class)
public class FooTest2 {
    private Foo foo;
    
    public FooTest2() {
        super();
    }
    
    @Before
    public void setUp() {
        foo = new Foo();
    }
    
    @Test
    public void testIncrement() throws Exception {
        PowerMockito.spy(Thread.class);
        PowerMockito.doThrow(new InterruptedException()).when(Thread.class);
        Thread.sleep(Mockito.anyLong());
        
        foo.increment();
        Assert.assertEquals(foo.getI(), 100);
    }

}