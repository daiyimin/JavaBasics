package com.test.Quiz;

/**
 * Refer to http://blog.csdn.net/kofsky/article/details/1882626
 * 
 * @author eyimdai
 */

public class TestClass {

    public static void main(String... args) {
        String a = "abc";
        String b = "abc";
        String c = new String(a);
        String d = "a" + "bc";
        
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a == d);
        
        String s1 = "a";
        String s2 = "bc";
        System.out.println(a == (s1+s2));
        System.out.println(a == (s1+s2).intern());
    }
}
