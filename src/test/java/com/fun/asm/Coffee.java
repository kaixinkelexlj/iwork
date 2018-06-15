/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.asm;

/**
 * @author lujun.xlj
 * @date 2017/10/9
 */
public class Coffee {

    int val;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}


    /*Last modified 2017-10-9; size 463 bytes
        MD5 checksum a5e87ee16a410a3c8c1ec691f374cbc8
        Compiled from "Coffee.java"
public class com.fun.asm.Coffee
        minor version: 0
        major version: 52
        flags: ACC_PUBLIC, ACC_SUPER
        Constant pool:
        #1 = Methodref          #4.#20         // java/lang/Object."<init>":()V
        #2 = Fieldref           #3.#21         // com/fun/asm/Coffee.val:I
        #3 = Class              #22            // com/fun/asm/Coffee
        #4 = Class              #23            // java/lang/Object
        #5 = Utf8               val
        #6 = Utf8               I
        #7 = Utf8               <init>
   #8 = Utf8               ()V
           #9 = Utf8               Code
           #10 = Utf8               LineNumberTable
           #11 = Utf8               LocalVariableTable
           #12 = Utf8               this
           #13 = Utf8               Lcom/fun/asm/Coffee;
           #14 = Utf8               getVal
           #15 = Utf8               ()I
           #16 = Utf8               setVal
           #17 = Utf8               (I)V
           #18 = Utf8               SourceFile
           #19 = Utf8               Coffee.java
           #20 = NameAndType        #7:#8          // "<init>":()V
           #21 = NameAndType        #5:#6          // val:I
           #22 = Utf8               com/fun/asm/Coffee
           #23 = Utf8               java/lang/Object
           {
           int val;
           descriptor: I
           flags:

public com.fun.asm.Coffee();
        descriptor: ()V
        flags: ACC_PUBLIC
        Code:
        stack=1, locals=1, args_size=1
        0: aload_0
        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
        4: return
        LineNumberTable:
        line 14: 0
        LocalVariableTable:
        Start  Length  Slot  Name   Signature
        0       5     0  this   Lcom/fun/asm/Coffee;

public int getVal();
        descriptor: ()I
        flags: ACC_PUBLIC
        Code:
        stack=1, locals=1, args_size=1
        0: aload_0
        1: getfield      #2                  // Field val:I
        4: ireturn
        LineNumberTable:
        line 19: 0
        LocalVariableTable:
        Start  Length  Slot  Name   Signature
        0       5     0  this   Lcom/fun/asm/Coffee;

public void setVal(int);
        descriptor: (I)V
        flags: ACC_PUBLIC
        Code:
        stack=2, locals=2, args_size=2
        0: aload_0
        1: iload_1
        2: putfield      #2                  // Field val:I
        5: return
        LineNumberTable:
        line 23: 0
        line 24: 5
        LocalVariableTable:
        Start  Length  Slot  Name   Signature
        0       6     0  this   Lcom/fun/asm/Coffee;
        0       6     1   val   I
        }
        SourceFile: "Coffee.java"*/
