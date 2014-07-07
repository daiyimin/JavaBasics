package com.ericsson.example.mockmock.hello;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ericsson.example.mockmock.client.ClientFactory;
import com.ericsson.example.mockmock.client.TwitterClient;

/**
 * @author honnix
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ClientFactory.class)
public class HardTest {
    private Hard hard;

    public HardTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        hard = new Hard();
    }

    @Test
    public void testHelloWorld() throws Exception {
        TwitterClient client = Mockito.mock(TwitterClient.class);
        Mockito.when(client.say()).thenReturn("I am nok!");
        //PowerMockito.whenNew(TwitterClient.class).withNoArguments().thenReturn(client);
        //PowerMockito.whenNew(TwitterClient.class).withNoArguments().thenReturn(client);
        PowerMockito.mockStatic(ClientFactory.class);
        Mockito.when(ClientFactory.getInstance()).thenReturn(client);

        System.out.print(hard.helloWorld());
        Assert.assertEquals("i said I am nok!", hard.helloWorld());
    }
}
