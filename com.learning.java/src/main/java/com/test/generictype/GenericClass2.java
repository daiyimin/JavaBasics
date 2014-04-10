package com.test.generictype;

public class GenericClass2<T> {

    class Child1 extends GenericClass2<Integer> {
        
    }
    
    /**
     * This is not allowed
     * A super class cannot use any wildcard
     */
/*    class Child2 extends GenericClass2<? extends Number> {
        
    }
*/
    
    class Child3 {
        /**
         * This is allowed
         * A field can use wildcard
         */
        private GenericClass2<? extends Number> value;
        /**
         * This is allowed
         * A parameter can use wildcard
         */
        public void setValue(GenericClass2<? extends Number> value) {
            this.value = value;
        }
    }
}
