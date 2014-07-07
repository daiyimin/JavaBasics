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
import com.ericsson.example.mockmock.client.TwitterClient;

/**
 * @author honnix
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Normal.class)
public class NormalTest {
    private Normal normal;

    public NormalTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        normal = new Normal();
    }

    @Test
    public void testHelloWorld() throws Exception {
        Client client = Mockito.mock(TwitterClient.class);
        //由于没有替换TwitterClient，所以接收不到"I am nok!"
        Mockito.when(client.say()).thenReturn("I am nok!");
        System.out.println("client:"+client.say());
        Assert.assertEquals("i said ", normal.helloWorld());
    }

    @Test
    public void testHelloWorld2() throws Exception {
        TwitterClient client = Mockito.mock(TwitterClient.class);
        Mockito.when(client.say()).thenReturn("I am nok!");
        PowerMockito.whenNew(TwitterClient.class).withNoArguments().thenReturn(client);
        System.out.print(normal.helloWorld());
        Assert.assertEquals("i said I am nok!", normal.helloWorld());
    }
}
