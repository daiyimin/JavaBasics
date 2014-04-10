package com.test.oom;

public class TestStackOverFlow {
    /**
     * (1) 在hotspot虚拟机中不区分虚拟机栈(-Xss)和本地方法栈(-Xoss),且只有对Xss参数的设置,才对栈的分配有影响
     * 
     * (2) 由于StackOverflowError->VirtualMachineError->Error ->Throwable,所以catch的时候如果用Exception的话将捕获不到异常 Stack length 会随着-Xss的减少而相应的变小
     */
    private int stackNumber1 = 1;

    public void stackLeck1() {
        stackNumber1++;
        stackLeck1();
    }

    public static void main(String[] args) {
        TestStackOverFlow jvmStackSOF = new TestStackOverFlow();
        try {
            jvmStackSOF.stackLeck1();
        } catch (Throwable ex) {
            System.out.println("Stack length:" + jvmStackSOF.stackNumber1);
            ex.printStackTrace();
        }
    }
}
