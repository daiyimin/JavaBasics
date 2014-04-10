package com.test.security.accesscontroller;

import java.io.FilePermission;
import java.security.Permission;

import sun.security.util.SecurityConstants;  

public class MySecurityManager extends SecurityManager {  
    // use this flag to avoid the circular authority check of MyAccessControler 
    private boolean loaded = false;
    
    @Override  
    public void checkRead(String file) {  
        checkPermission(new FilePermission(file,   
                SecurityConstants.FILE_READ_ACTION));  
        
    }  
  
    @Override  
    public void checkPermission(Permission perm) { 
        if (!loaded) {
            loaded = true;
            MyAccessControler.checkPermission(perm);  
        }
    }        
}
