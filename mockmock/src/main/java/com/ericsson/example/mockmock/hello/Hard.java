package com.ericsson.example.mockmock.hello;

import com.ericsson.example.mockmock.client.ClientFactory;

/**
 * @author honnix
 */
public class Hard {
    public Hard() {
        super();
    }

    public String helloWorld() {
        return "i said " + ClientFactory.getInstance().say();
    }
}
