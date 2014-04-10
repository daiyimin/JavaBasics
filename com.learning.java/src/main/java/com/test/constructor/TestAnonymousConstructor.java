package com.test.constructor;

public class TestAnonymousConstructor {
	/**
	 * This is a static domain code
	 * It doesn't need any object instance to be created.
	 * It is called when the class is loaded by class loader and is initialized?
	 */
	static {
		System.out.println("I am static code");
	}
	
	public Integer number = null;
	
	// a normal constructor
	public TestAnonymousConstructor() {
		System.out.println(number);
	}
	
	/**
	 * This is an anonymous constructor which is called before other constructors
	 */
	{
		number = new Integer(100);
	}
	
	public static void main(String...strings ) {
//		new TestAnonymousConstructor();
	}
}
