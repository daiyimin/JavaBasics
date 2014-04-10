package com.test.oom;

import java.util.ArrayList;
import java.util.List;

public class TestHeapOutOfMemory {
    public static void main(String[] args) {
        headOutOfMemory();
    }
    
    /*
     * -verbose:gc -XX:+PrintGCDetails 
     * -XX:+HeapDumpOnOutOfMemoryError
     * -Xms20m -Xms20m
     */
    static void headOutOfMemory() {
        long count = 0;
        try {
            
            List<Object> objects = new ArrayList<Object>();
            while (true) {
                count++;
                objects.add(new Object());
            }
        } catch (Throwable ex) {
            System.out.println(count);
            ex.printStackTrace();
        }
    }
    
}
