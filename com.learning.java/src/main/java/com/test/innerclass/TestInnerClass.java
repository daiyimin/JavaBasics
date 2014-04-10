package com.test.innerclass;

/**
 * 内部类不管方法的可见性如何，那怕是public，除了包容类，其他类都无法使用它
 * 
 * @author eyimdai
 */
public class TestInnerClass {
    public class Inner1 {
        
    }
    
    public static class Inner2 {
        
    }
    

    public static void main(String[] args) {
        TestInnerClass tester = new TestInnerClass();
        
        
        // non static inner class need an instance of its outer class
        Inner1 inner1 = tester.new Inner1();
//        Inner1 inner1 = new TestInnerClass.Inner1();
        
        // static inner class don't need an instance of its outer class
        Inner2 inner2a = new Inner2(); // short form because you are in a static method of TestInnerClass
        Inner2 inner2b = new TestInnerClass.Inner2();
        
        Inner3 inner3 = new Inner3(tester);
    }
}


class Inner3 extends TestInnerClass.Inner1 {
    // 内部类被继承,由于内部类有一个指向外围类对象的秘密引用，所以在继承内部类的时候，该秘密引用必须被初始化。
    // 解决方法是enclosingClassReference.super();语法
    Inner3 (TestInnerClass out) {
        out.super();
    }
}
