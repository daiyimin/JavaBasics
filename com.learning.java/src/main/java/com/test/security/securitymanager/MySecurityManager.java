package com.test.security.securitymanager;

public class MySecurityManager extends SecurityManager {

    @Override
    public void checkRead(String file) {
        //super.checkRead(file, context);
        if (file.endsWith("test"))  
        throw new SecurityException("You don't have right to do so!");  
    }
    
}
