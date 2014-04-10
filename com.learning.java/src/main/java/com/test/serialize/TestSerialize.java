package com.test.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestSerialize {
    private static void writeObject(Object user) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("user.bin"));
            output.writeObject(user);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void readObject() {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("user.bin"));
            User user = (User) input.readObject();
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static void main(String... args) {
        User user = new User();
        user.setUsername("Yimin");
        user.setNickname("David");
        
        writeObject(user);
        readObject();
    }
}
