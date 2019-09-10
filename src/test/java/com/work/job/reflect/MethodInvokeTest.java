/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job.reflect;

import java.lang.reflect.Method;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.junit.Test;

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
    System.out.println(method.getReturnType());
    System.out.println(method.invoke(target));

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

  @Test
  public void testFastMethod() throws Exception {
    FastClass fastClass = FastClass.create(MethodFun.class);
    FastClass fastClass1 = FastClass.create(MethodFun.class);
    System.out.println(fastClass == fastClass1);
    FastMethod fastMethod = fastClass.getMethod("say", new Class[]{String.class, Object[].class});
    fastMethod.invoke(new MethodFun(), new Object[]{"hello %s", new Object[]{"xlj"}});

    FastMethod fastMethodStaic = fastClass
        .getMethod("sayStatic", new Class[]{String.class, Object[].class});
    fastMethodStaic.invoke(null, new Object[]{"hello %s", new Object[]{"xlj"}});
  }

  public static class MethodFun {

    private void say() {
      System.out.println("I am stealer");
    }

    public void say(String msg) {
      System.out.println(msg);
    }

    public void say(String msg, Object... args) {
      System.out.println(String.format(msg, args));
    }

    public static void sayStatic(String msg, Object... args) {
      System.out.println(String.format(msg, args));
    }

  }

}
