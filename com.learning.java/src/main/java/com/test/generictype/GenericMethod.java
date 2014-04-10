package com.test.generictype;

public class GenericMethod {
    public static final Object NULL_KEY = new Object();
    
    /**
     * 
     * @param <T> generic type T, it is decided by the return value type
     * @param defaultValue
     * @return
     */
    public static <T> T get(Object defaultValue) {
        return (T) (defaultValue == NULL_KEY ? null : defaultValue);
    }
    
    public static void main(String[] args) {
        Object o = GenericMethod.get(NULL_KEY);
        System.out.println(o);
        
        String s = GenericMethod.get("hello world");
        System.out.println(s);
        
        Integer i = GenericMethod.get(new Integer(1));
        System.out.println(i);
        
        Double d = GenericMethod.get(new Double(1.1));
        System.out.println(d);
        
        
        // This test verifies that generic type checking cannot find all error at compile time
/*        String test = GenericMethod.get(new Double(1.1));
        System.out.println(test);
*/
    }
}
