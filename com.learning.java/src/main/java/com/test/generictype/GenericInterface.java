package com.test.generictype;


/*
 * All the members fields of interface are by default public, static, final<BR/>
 * Since inner interface is static by default, you can't refer T from static fields & methods<BR/>
 * Because T is actually associated with instance of class, if it were associated with static field or method which is associated with class then it wouldn't make any sense
 */
public interface GenericInterface<T> {
    // Results in non-static type T cannot be referenced from a static context
//    T field;

    // method is not static
    public T getValue();
    
    interface B {
       // Results in non-static type T cannot be referenced from a static context
//       T foo();
       
       // You can still define a static generic method because the generic type T is defined on the method. 
       // In this example, T on bar() is different from T on GenericInterface.
       <T> T bar();
    }

}