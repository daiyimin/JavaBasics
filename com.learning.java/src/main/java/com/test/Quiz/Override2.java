package com.test.Quiz;

class Father {
    /**
     * This is a <strong>public</strong> method<BR/>
     * When a class instance is created, JVM will allocate heap memory for the class instance.<BR/>
     * A virtual method table of all public methods is stored in class instance heap memory.<BR/>
     * 
     * So the 多态适用
     */
    public void method() {
        System.out.println("method of Father1 calling from " + 
            this.getClass().getName());
        System.out.println("Hi I am father");

    }

}

class Son extends Father {
    public void method() {
        System.out.println("method of Son1 calling from " + 
            this.getClass().getName());
    }
}


public class Override2 {

    public static void main(String[] args) {
        Father f = new Son();
        f.method();
    }
}