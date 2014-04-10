package com.test.innerclass;

/**
·在使用匿名内部类时，要记住以下几个原则： 
·匿名内部类不能有构造方法。   因为没有构造函数，所以它必须完全借用父类的构造函数来实例化
·匿名内部类不能定义任何静态成员、方法和类。   
·匿名内部类不能是public,protected,private,static。   
·只能创建匿名内部类的一个实例。 
·一个匿名内部类一定是在new的后面，用其隐含实现一个接口或实现一个类。   
·因匿名内部类为局部内部类，所以局部内部类的所有限制都对其生效。  
·我们会用到一些内部类和匿名类。当在匿名类中用this时，这个this则指的是匿名类或内部类本身。 这时如果我们要使用外部类的方法和变量的话，则应该加上外部类的类名
·匿名内部类是没有名字的内部类,不能继承其它类
      */
public class TestAnonymousInnerClass {
    
    class Inner {
        
    }
    
    interface InnerI {
        
    }
    
    public void foo() {
        
    }
    
    public void testAnonymousInnerClass1() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // do something
                TestAnonymousInnerClass.this.foo();
            }
        };
    }
    
    public void testAnonymousInnerClass2() {
        InnerI inner = new InnerI () {
            
        };
    }
}
