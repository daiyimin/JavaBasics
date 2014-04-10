package com.test.security.securitymanager;

/**
 * before running, add -Djava.security.manager in VM arguments
 * 
 * @author eyimdai
 */
public class TestGetSecurityManager {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(System.getSecurityManager());  
    }

}
