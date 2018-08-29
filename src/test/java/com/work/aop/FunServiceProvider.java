package com.work.aop;

/**
 * @author xulujun
 * @date 2018/08/29
 */
@FunService
public class FunServiceProvider {

  public String hello() throws Exception {
    System.out.println("FunServiceProvider==>hello");
    return "FunServiceProvider==>hello";
  }

  @IgnoreFun
  public String world() throws Exception {
    System.out.println("FunServiceProvider==>world");
    return "FunServiceProvider==>world";
  }

}
