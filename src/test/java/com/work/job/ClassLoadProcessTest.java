/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job;

/**
 * @author lujun.xlj
 * @date 2017/7/4
 */
public class ClassLoadProcessTest {

    private static int       a = 100;
    private static final int b = 200;
    private static int       c;

    static {
        c = 300;
    }

    private int              d = 500;
    private int              e;

    public ClassLoadProcessTest(){
        e = 1000;
    }

    public void fun() {
        System.out.println(e);
    }

    public static void main(String[] args) {
        new ClassLoadProcessTest().fun();
    }

}
