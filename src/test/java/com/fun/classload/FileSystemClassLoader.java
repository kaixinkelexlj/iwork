package com.fun.classload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemClassLoader extends ClassLoader {

    private String rootDir;

    public FileSystemClassLoader(String rootDir){
        this.rootDir = rootDir;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassData(String className) {
        String path = classNameToPath(className);
        try {
            @SuppressWarnings("resource")
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        return rootDir + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
    }
    
    public static void main(String[] args) {
        String classDataRootPath = "src\\test\\java\\";
        FileSystemClassLoader fscl1 = new FileSystemClassLoader(classDataRootPath); 
        FileSystemClassLoader fscl2 = new FileSystemClassLoader(classDataRootPath); 
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
