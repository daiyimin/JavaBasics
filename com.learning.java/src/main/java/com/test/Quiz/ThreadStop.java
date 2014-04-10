package com.test.Quiz;

public class ThreadStop extends Thread {

    public void print() {
        System.out.println("print");
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        print();
    }

    public static void main(String[] args) throws 
            InterruptedException {
        ThreadStop object = new ThreadStop();
        object.start();
//        object.join();
//        object.stop();
//        object.notify();
//        object.interrupt();
    }
      
}
