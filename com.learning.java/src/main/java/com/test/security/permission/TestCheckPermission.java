package com.test.security.permission;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.util.Enumeration;

public class TestCheckPermission {
    private static void printPermissions() {
        URL codebase = null;
        try {
            // Get permissions for a directory
            codebase = new URL("file:C:/Ericsson/maworkspace/com.learning.java/target/classes/*");

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }

        CodeSource cs = new CodeSource(codebase, (java.security.cert.Certificate[]) null);

        PermissionCollection pcoll = Policy.getPolicy().getPermissions(cs);

        // View each permission in the permission collection
        Enumeration<Permission> enum1 = pcoll.elements();
        for (; enum1.hasMoreElements();) {
            Permission p = (Permission) enum1.nextElement();
            System.out.println(p);
        }
    }

    private static boolean checkPermissions(Permission perm) {
        URL codebase = null;
        try {
            // Get permissions for a directory
            codebase = new URL("file:C:/Ericsson/maworkspace/com.learning.java/target/classes/*");

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }

        CodeSource cs = new CodeSource(codebase, (java.security.cert.Certificate[]) null);

        PermissionCollection pcoll = Policy.getPolicy().getPermissions(cs);

        // View each permission in the permission collection
        Enumeration<Permission> enum1 = pcoll.elements();
        for (; enum1.hasMoreElements();) {
            Permission p = (Permission) enum1.nextElement();
            if (p instanceof java.security.AllPermission) {
                continue;
            }
            if (p.implies(perm)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add following VM arguments: -Djava.security.manager -Djava.security.policy=src/main/java/com/test/security/permission/java.policy Make sure that you give
     * all permission authority to this program in java.policy Otherwise it will not have the right to read the permission information.
     */
    public static void main(String[] args) {
        printPermissions();

        if (checkPermissions(new FilePermission("C:/Ericsson/maworkspace/com.learning.java/answer.txt", "read"))) {
            System.out.println("You get the permission!");
        }

    }

}
