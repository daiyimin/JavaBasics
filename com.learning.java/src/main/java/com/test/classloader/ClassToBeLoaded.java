package com.test.classloader;

public class ClassToBeLoaded {
    static{
        System.out.println("TestBeLoader init");
    }
    public void sayHello(){
        System.out.println("hello");
    }
}
