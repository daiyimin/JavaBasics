package com.ericsson.example.mockmock.hello;

import com.ericsson.example.mockmock.client.Client;
import com.ericsson.example.mockmock.client.TwitterClient;

/**
 * @author honnix
 */
public class Normal {
    public Normal() {
        super();
    }

    public String helloWorld() {
        Client client = new TwitterClient();
        return "i said " + client.say();
    }
}
