package com.test.security.securitymanager;

import java.io.FileInputStream;
import java.io.IOException;

public class TestMySecurityManager {
    public static void main(String[] args) {
        System.setSecurityManager(new MySecurityManager());
        try {
            FileInputStream fis = new FileInputStream("test");
            System.out.println(fis.read());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
