package com.test.security.policy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NoPermissionToWrite {
    public void writeFile() throws IOException {
        File data=new File( "data.txt" );
        FileWriter writer=new FileWriter(data);
        writer.write("this is a test" );
        writer.close();
    }
}
