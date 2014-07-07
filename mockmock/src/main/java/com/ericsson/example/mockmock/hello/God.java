package com.ericsson.example.mockmock.hello;

import com.ericsson.example.mockmock.client.Client;
import com.ericsson.example.mockmock.client.TwitterClient;

/**
 * @author honnix
 */
public class God {
    public God() {
        super();
    }

    public String helloWorld() {
        Client client = new TwitterClient();
        String echo = client.say();

        if ("confirmed".equals(echo)) {
            client.sayIt("are you sure you're gonna catch what i'm going to say, this could be pretty long");
            client.sayIt("goodbye");
        } else if ("repeat".equals(echo)) {
            client.say();
        } else if ("gfwed".equals(echo)) {
            for (int i = 0; i < 1000; ++i) {
                client.sayIt("DOS attack");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        }
        
        return "what i said does not matter really";
    }
}
