package com.test.Quiz;

public class ClassInitialize {
    public static void main(String... args) {
        new Child("ericsson");
    }
}

class Parent {
    public void sayHi() {
        System.out.println("Parent.sayHi()");
    }
    public Parent() {
        System.out.println("Parent constrctor before sayHi()");
        sayHi();
        System.out.println("Parent constrctor after sayHi()");
    }
}

class Child extends Parent {
    private String name = "default";

    public Child(String s) {
        this.name = s;
        System.out.println("Child constrctor, name = " + this.name);
    }
    public void sayHi() {
        System.out.println("Child[" + this.name + "].sayHi()");
    }
}
