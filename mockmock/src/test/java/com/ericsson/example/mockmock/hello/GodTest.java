package com.ericsson.example.mockmock.hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ericsson.example.mockmock.client.Client;
import com.ericsson.example.mockmock.client.TwitterClient;

/**
 * @author honnix
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(God.class)
public class GodTest {
    private God god;
    
    public GodTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        god = new God();
    }

    @Test
    public void testConfirm() throws Exception {
        Client client = Mockito.mock(TwitterClient.class);
        Mockito.when(client.say()).thenReturn("confirmed");
        PowerMockito.whenNew(TwitterClient.class).withNoArguments().thenReturn((TwitterClient)client);
        god.helloWorld();
        Mockito.verify(client, Mockito.times(1)).sayIt(Matchers.contains("you sure you're gonna"));
        Mockito.verify(client, Mockito.times(1)).sayIt("goodbye");
    }
    
    @Test
    public void testRepeat() throws Exception {
        Client client = Mockito.mock(TwitterClient.class);
        Mockito.when(client.say()).thenReturn("repeat");
        PowerMockito.whenNew(TwitterClient.class).withNoArguments().thenReturn((TwitterClient)client);
        god.helloWorld();
        Mockito.verify(client, Mockito.atLeastOnce()).say();
        Mockito.verify(client,Mockito.times(2));
    }
    
    @Test
    public void testGfwed() throws Exception {
        Client client = Mockito.mock(TwitterClient.class);
        Mockito.when(client.say()).thenReturn("gfwed");
        PowerMockito.whenNew(TwitterClient.class).withNoArguments().thenReturn((TwitterClient)client);
        
        PowerMockito.mockStatic(Thread.class);
//        PowerMockito.doNothing().when(Thread.class, "sleep", 10L);

        
        
        god.helloWorld();
        Mockito.verify(client, Mockito.times(1000)).sayIt("DOS attack");
        
    }
}
