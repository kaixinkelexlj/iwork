/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.asm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import org.junit.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author lujun.xlj
 * @date 2017/8/4
 */
public class AsmTest {

    public static void main(String[] args) {

        System.out.println("hello asm");

    }

    @Test
    public void generateClass() throws Exception {

        //param signature ==> the method's signature. May be null if the method parameters, return type and exceptions do not use generic types.

        ClassWriter classWriter = new ClassWriter(0);
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE, "com/asm3/Comparable", null, "java/lang/Object", new String[] { "com/asm3/Mesurable" });
        classWriter.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, "LT", "I", null, -1).visitEnd();
        classWriter.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, "EQ", "I", null, 0).visitEnd();
        classWriter.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, "GT", "I", null, 1).visitEnd();
        classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null);
        classWriter.visitEnd();
        byte[] data = classWriter.toByteArray();
        String clazzPath = "D://downloads//Comparable.class";
        File file = new File(clazzPath);
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();

        ProcessBuilder processBuilder = new ProcessBuilder("javap", "-c", clazzPath);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        int code = process.waitFor();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        bufferedReader.lines().forEach(System.out::println);
        System.exit(code);

    }

    @Test
    public void testAsmType() throws Exception {

        System.out.println(Type.getInternalName(String.class));
        Method method = AsmTest.class.getDeclaredMethod("main", String[].class);
        System.out.println(Type.getMethodDescriptor(method));

    }
}
