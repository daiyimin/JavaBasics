package com.test.classloader;

public class TestBeLoader {
    static{
        System.out.println("TestBeLoader init");
    }
    public void sayHello(){
        System.out.println("hello");
    }
}
