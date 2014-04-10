package com.test.security.accesscontroller;

import java.io.FilePermission;
import java.security.AccessControlException;
import java.security.Permission;
import sun.security.util.SecurityConstants;

public class MyAccessControler {
    public static void checkPermission(Permission perm) throws AccessControlException {
        Permission perAll = new FilePermission("c:/tmp/*", SecurityConstants.FILE_READ_ACTION);
        if (perAll.implies(perm)) {
            System.out.println("You have authority!");
        } else {
            throw new AccessControlException("You don't have authority!");
        }
    }
}