package com.test.generictype;

/**
 * T is generic type
 * 
 * @author eyimdai
 */
public class GenericClass<T extends Number> {
    private T field;
    
    public GenericClass(T init) {
        field = init;
    }
    
    public T getField() {
        return field;
    }
    
    /**
     * @param <T> The <T> on getValue() is different from <T extends Number> on GenericClass<BR/>
     * Try to change <T> to <K> and see what happens and explain why?
     * @param value
     * @return
     */
//    public <K> T getValue(T value) {
    public <T> T getValue(T value) {
        return value;
    }
    
    public static void main(String... args) {
        GenericClass<Integer> gc1 = new GenericClass<Integer>(new Integer(1));
        System.out.println(gc1.getField());
        
        GenericClass<Double> gc2 = new GenericClass<Double>(new Double(1.1));
        System.out.println(gc2.getField());
        
        // getValue() returns String instead of Double!
        String s = gc2.getValue(new String("hello world"));
        System.out.println(s);
    }
}
