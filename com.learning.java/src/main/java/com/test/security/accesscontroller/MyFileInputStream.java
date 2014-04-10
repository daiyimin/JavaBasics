package com.test.security.accesscontroller;

import java.io.File;
import java.io.FileNotFoundException;

public class MyFileInputStream {

    public MyFileInputStream(String name) throws FileNotFoundException {
        this(name != null ? new File(name) : null);
    }

    public MyFileInputStream(File file) throws FileNotFoundException {
        String name = (file != null ? file.getPath() : null);
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkRead(name);
        }
    }
}