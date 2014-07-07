package com.ericsson.example.mockmock.hello;

import com.ericsson.example.mockmock.client.Client;

/**
 * @author honnix
 */
public class Beginner {
    public Beginner() {
        super();
    }

    public String helloWorld(Client client) {
        return "i said " + client.say();
    }
}
