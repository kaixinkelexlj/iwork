package com.fun.reflections.testmodel;

import com.fun.reflections.annotations.TestValue;

/**
 * @author xulujun 2019/10/09
 */
//@TestBean
public class ConfigBean implements SuperBean {

  @TestValue
  public static int val = 100;

  public static int ignore1 = 2;

  private static int ignore2 = 2;

}
