package com.ericsson.example.mockmock.hello;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ericsson.example.mockmock.client.Client;

/**
 * @author honnix
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Grueling.class})
public class GruelingTest {
    private Grueling grueling;

    public GruelingTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        grueling = new Grueling();
    }

    @Test
    public void testHelloWorld() throws Exception {
        Client client = Mockito.mock(Client.class);
        Mockito.when(client.say()).thenReturn("I am nok!");
        //PowerMockito.whenNew(TwitterClient.class).withNoArguments().thenReturn(client);
        //PowerMockito.whenNew(TwitterClient.class).withNoArguments().thenReturn(client);
        //PowerMockito.whenNew(Grueling.class).withNoArguments().thenThrow(new InterruptedException());
        //PowerMockito.doThrow(grueling.helloWorld(client));        
        //Mockito.when(ClientFactory.getInstance()).thenReturn(client);

        PowerMockito.mockStatic(Thread.class);
        System.out.print(grueling.helloWorld(client));
//        PowerMockito.mockStatic(Thread.class);
        Assert.assertEquals("i said I am nok!", grueling.helloWorld(client));
    }
}
