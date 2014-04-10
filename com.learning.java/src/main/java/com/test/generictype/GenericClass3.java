package com.test.generictype;

import java.util.List;
import java.util.WeakHashMap;

public class GenericClass3<K, V> {
    public static final Object NULL_KEY = new Object();
    
    private K key;
    private V value;
    
    public GenericClass3(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    /**
    * Because K is actually associated with instance of class, if it were associated with static field or method which is associated with class then it wouldn't make any sense
    */
    private static <K> K unmaskNull(Object key) {
        return (K) (key == NULL_KEY ? null : key);
    }
    
    private static class Entry<K,V> {
        public K getKey() {
            return GenericClass3.<K>unmaskNull(GenericClass3.NULL_KEY);
        }
    }
    
    public static void main(String... args) {
        GenericClass3.unmaskNull(GenericClass3.NULL_KEY);
    }

}
