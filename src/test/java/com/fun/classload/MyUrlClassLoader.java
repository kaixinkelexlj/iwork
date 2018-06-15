package com.fun.classload;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyUrlClassLoader extends URLClassLoader{

    public MyUrlClassLoader(URL[] urls){
        super(urls);
    }
    
    /**
     * Constructs a new URLClassLoader for the specified URLs using the default delegation parent ClassLoader. The URLs will be searched in the order specified for classes and resources after first searching in the parent class loader. Any URL that ends with a '/' is assumed to refer to a directory. Otherwise, the URL is assumed to refer to a JAR file which will be downloaded and opened as needed.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        File clazzFile = new File("src\\test\\java\\");
        if(!clazzFile.exists())
            throw new FileNotFoundException("class path not found!");
        URL[] urls = new URL[] { clazzFile.toURI().toURL() };
        MyUrlClassLoader fscl1 = new MyUrlClassLoader(urls); 
        MyUrlClassLoader fscl2 = new MyUrlClassLoader(urls); 
        String className = "com.fun.classload.StaticFieldTest";    
        try { 
            Class<?> class1 = fscl1.loadClass(className); 
            Object obj1 = class1.getConstructor(String.class).newInstance("hello"); 
            Object obj3 = class1.getConstructor(String.class).newInstance("hello"); 
            Class<?> class2 = fscl2.loadClass(className); 
            Object obj2 = class2.getConstructor(String.class).newInstance("hello"); 
            System.out.println(obj1.equals(obj2));
            System.out.println(obj1.equals(obj3));
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
    
}
