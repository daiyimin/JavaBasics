package com.test.generictype;

import java.util.ArrayList;
import java.util.List;

public class TestGenericType {

    public static void printList(List<Object> list) {
        for (Object o : list) {
            System.out.println(o);
        }
        list.add(1);
    }
    
    public static void printList2(List<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
    }
    
    public static void printArray(Object[] objs) {
        for (Object o : objs) {
            System.out.println(o);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Object o = 1;
        int i = 1;
        
/*      List<String> cannot be converted to List<Object>
 *      Because List<Object> can be {String, Integer, Long, ...}, 
 *      so the operation on List<Object> maybe not applicable on List<String>
 *      For example, following operation is legal on List<Object>.add(new Integer(1)
 *      But it is not legal on Lis<String>
 *      If you convert a List<String> to List<Object>, JVM doesn't know if it can add an Integer to the List anymore  
 */    
//        printList(new ArrayList<String>());

        /* on the contrary, cannot convert List<Object> to List<String> too
         * because there could be Integer element in List<Object>
         */
//        printList2(new ArrayList<Object>());
        
        /*
         * String[] can be converted to Object[]
         */
        printArray(new String[]{"a", "b"});
        
    }
}
