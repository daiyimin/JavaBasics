package com.ericsson.example.mockmock.hello;

public class Foo {
    private int i = 0;
    
    public Foo() {
        
    }
    
    public void increment() {
        try {
            Thread.sleep(10000);
            int account = Thread.activeCount();
            System.out.println(account);
        } catch (InterruptedException e) {
        }
        i += 100;
    }
    
    public int getI() {
        return i;
    }
}
