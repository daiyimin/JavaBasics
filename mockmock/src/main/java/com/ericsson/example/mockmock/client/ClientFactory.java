package com.ericsson.example.mockmock.client;

/**
 * @author honnix
 */
public final class ClientFactory {
    public static Client getInstance() {
        return new TwitterClient();
    }

    private ClientFactory() {
        super();
    }
}
