package com.test.security.accesscontroller;

import java.io.FileInputStream;  
import java.io.IOException;  
import java.security.ProtectionDomain;  
  
public class TestMySecurityManager {  
    public static void main(String[] args) {  
        System.setSecurityManager(new MySecurityManager());  
        
//        try {  
//            MyFileInputStream fis = new MyFileInputStream("c:/tmp1/test.txt");  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
        
        try {  
            MyFileInputStream fis = new MyFileInputStream("c:/tmp/test.txt");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  

    }  
}  
