package com.test.Quiz;


public class Override3 {
    /**
     * This is a <strong>private</strong> method<BR/>
     * 所以多态并不适用
     */
    private void method() {
        System.out.println("method of Father1 calling from " + 
            this.getClass().getName());
    }

    public static void main(String[] args) {
        Override3 a = new Son1();
        a.method();

        Son1 b = new Son1();
        b.method();
    }
}

class Son1 extends Override3 {
    public void method() {
        System.out.println("method of Son1 calling from " + 
            this.getClass().getName());
    }
}
