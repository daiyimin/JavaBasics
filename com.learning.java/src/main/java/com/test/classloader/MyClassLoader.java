package com.test.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * MyClassLoader will only load one class: com.test.classloader.TestBeLoader
 *  
 * @author eyimdai
 */
public class MyClassLoader extends ClassLoader {
    private Map<String, Class<?>> loadedByMe = new HashMap<String, Class<?>>();
    
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = getByteArray(name);  
        if (data == null) {  
            throw new ClassNotFoundException();  
        }  
        return defineClass(name, data, 0, data.length); 
    }
    
    /**
     * read byte code from the target folder
     * put the content of TestBeLoader.class into a byte array
     * @param name
     * @return
     */
    private byte[] getByteArray(String name){
        String filePath = "target\\classes\\";   
        filePath += name.replace(".", File.separator);
        filePath += ".class";
        byte[] buf = null;
        try {
            File f = new File(filePath);
            String path = f.getAbsolutePath();
            FileInputStream in = new FileInputStream(filePath);
            buf = new byte[in.available()];
            in.read(buf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

    // check if TestBeLoader is already loaded
    protected final Class<?> findLoadedClassByMe(String name) {
        return loadedByMe.get(name);
    }

    public synchronized Class<?> loadClass(String name)
    throws ClassNotFoundException    {
        // First, check if the class has already been loaded by me
        Class c = findLoadedClassByMe(name);
        if (c == null) {
            try {
                if (name.equals("com.test.classloader.ClassToBeLoaded")) {
                    c = findClass(name);
                    if (c != null) {
                        loadedByMe.put(name, c);
                    }
                } else {
                    // for other class, let parent classloader handle it
                    c = this.getParent().loadClass(name);
                }
            } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
            }
        }
        return c;
    }
}

/**
    Another kind of customized classloader is load class file from a special folder.
    Because all parent classloader don't load class from that place, only customized classloader will load it.
*/