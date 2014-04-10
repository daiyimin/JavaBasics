package jmxbook.ch7b;

public class Server {

    private long startTime;

    public Server() {
    }

    public int start() {
        startTime = System.currentTimeMillis();
        return 0;
    }

    public long getUpTime() {
        return System.currentTimeMillis() - startTime;
    }
}
