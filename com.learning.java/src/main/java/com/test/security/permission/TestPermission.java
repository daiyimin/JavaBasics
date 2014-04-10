package com.test.security.permission;

import java.io.FilePermission;
import java.security.Permission;

public class TestPermission {

    public static void main(String[] args) {  
        
        Permission perOne = new FilePermission("c:/tmp/test.txt","read");  
        Permission perAll = new FilePermission("c:/tmp/*","read");  
          
        System.out.println(perOne.implies(perAll));  
        System.out.println(perAll.implies(perOne));  
    }  
}
