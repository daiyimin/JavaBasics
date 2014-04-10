package com.test.security.policy;

import java.io.IOException;

/**
 * before running, add following arguments<BR/>
 * <Strong>-Djava.security.manager  -Djava.security.policy=src/main/java/com/test/security/policy/java.policy</Strong><BR/>
 * in VM arguments<BR/>
 * If you change the program running home, please change java.policy file accordingly
 * @author eyimdai
 */
public class TestJavaSecurityPolicy {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TestJavaSecurityPolicy.class and PermissionToWrite.class are granted write permission to data.txt
        // The file write operation is permitted.
        PermissionToWrite perm = new PermissionToWrite();
        perm.writeFile();
       
        // TestJavaSecurityPolicy.class is granted write permission to data.txt
        // But NoPermissionToWrite.class in com.learning.jar.jar is not permitted
        // So the file write operation is forbidden
        NoPermissionToWrite noPerm = new NoPermissionToWrite();
        noPerm.writeFile();
    }
}
