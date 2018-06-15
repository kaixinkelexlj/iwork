/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job.reflect;

import java.lang.reflect.Method;

/**
 * @author lujun.xlj
 * @date 2017/7/17
 */
public class MethodInvokeTest {

    public static void main(String[] args) throws Exception {
        MethodFun target = new MethodFun();

        //1次
        Method method = MethodFun.class.getDeclaredMethod("say");
        method.setAccessible(true);
        method.invoke(target);

        //13次
        for (int i = 0; i <= 13; i++) {
            method = MethodFun.class.getDeclaredMethod("say");
            method.setAccessible(true);
            method.invoke(target);
        }

        //1次（第15次）
        method = MethodFun.class.getDeclaredMethod("say");
        method.setAccessible(true);
        method.invoke(target);

        //16次
        method = MethodFun.class.getDeclaredMethod("say");
        method.setAccessible(true);
        method.invoke(target);

    }

    public static class MethodFun {

        private void say() {
            System.out.println("I am stealer");
        }

    }

}
