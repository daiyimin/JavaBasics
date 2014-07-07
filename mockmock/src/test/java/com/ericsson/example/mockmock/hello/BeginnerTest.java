package com.ericsson.example.mockmock.hello;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ericsson.example.mockmock.client.Client;

/**
 * @author honnix
 */
public class BeginnerTest {
    private Beginner beginner;

    public BeginnerTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        beginner = new Beginner();
    }

    @Test
    public void testHelloWorld() throws Exception {
        Client client = Mockito.mock(Client.class);
        Mockito.when(client.say()).thenReturn("I am nok!");
        Assert.assertEquals("i said I am nok!", beginner.helloWorld(client));
    }
}
