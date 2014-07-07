package com.ericsson.example.mockmock.hello;

import com.ericsson.example.mockmock.client.Client;

/**
 * @author honnix
 */
public class Grueling {
    public Grueling() {
        super();
    }

    public String helloWorld(Client client) {
        try {
            Thread.sleep(1000 * 3600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "i said " + client.say();
    }
}
