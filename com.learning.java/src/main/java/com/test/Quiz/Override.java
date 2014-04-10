package com.test.Quiz;

/**
 * static method is stored in Method Area, together with class object(not instance)<BR/>
 * so static method cannot be overriden<BR/>
 * 
 * 静态方法是类在加载时就被加载到内存中的方法，在整个运行过程中保持不变，因而不能重写。但非静态方法是在对象实例化时才单独申请内存空间，为每一个实例分配独立的运行内存，因而可以重写
 * 
 * @author eyimdai
 */
class E {
    public static String staticMethod() {
        return "E staticMethod()";
    }
    public String dynamicMethod() {
        return "E dynamicMethod()";
    }
}

class F extends E {
    public static String staticMethod() {
        return "F staticMethod()";
    }
    public String dynamicMethod() {
        return "F dynamicMethod()";
    }
}

public class Override {
    public static void main(String...args) {
        E e = new F();
        System.out.println(e.staticMethod());
        System.out.println(e.dynamicMethod());
    }
}
