package com.ericsson.example.mockmock.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author honnix
 */
public class TwitterClient implements Client {
    public TwitterClient() {
        super();
    }

    @Override
    public String say() {
        return sayIt("hello world");
    }

    @Override
    public String sayIt(String it) {
        String echo = "";

        try {
            Socket socket = new Socket("twitter.com", 80);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.write(it);
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            echo = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return echo;
    }
}
