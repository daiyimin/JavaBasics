package com.test.classloader;

public class TestClassLoaderDemo {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Class thisCls = TestClassLoaderDemo.class;
        MyClassLoader myClassLoader = new MyClassLoader();
        System.out.println(thisCls.getClassLoader());
        System.out.println(myClassLoader.getParent());
        try {
            //用自定义的类装载器来装载类,这是动态扩展的一种途径
            Class cls1 = myClassLoader.loadClass("com.test.classloader.ClassToBeLoaded");
            System.out.println(cls1.getClassLoader());
            Object obj = cls1.newInstance();
            
            Class cls2 = ClassToBeLoaded.class;
            
            System.out.println("classloader of cls1: " + cls1.getClassLoader());
            System.out.println("classloader of cls2: " + cls2.getClassLoader());
            // class cast error will happen, because the classloader of cls1 and cls2 is different
            ClassToBeLoaded test=(ClassToBeLoaded)obj;
            /**
             *  There is one way to work around this cast error.
             *  Define an interface and let TestBeLoaded to implement the interface.
             *  You can cast TestBeLoaded loaded by MyClassLoader to the interface with no problem.
             *  Because the interface is loaded by system class loader always.
             */
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
