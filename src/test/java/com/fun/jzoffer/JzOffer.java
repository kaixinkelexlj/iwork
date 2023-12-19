package com.fun.jzoffer;

import com.alibaba.fastjson.JSON;
import java.util.function.Supplier;
import org.junit.Assert;

/**
 * @author xulujun 2020/07/11.
 */
public class JzOffer {

  public static void testAndPrintout(boolean condition, Object obj) {
    if (obj == null) {
      System.out.println("<null>");
    }
    if (obj instanceof String) {
      System.out.println(obj);
    } else {
      System.out.println(JSON.toJSONString(obj));
    }
    Assert.assertTrue(condition);
  }

  public static void testAndPrintout(Supplier<Boolean> condition, Object obj) {
    testAndPrintout(condition.get(), obj);
  }

  public void sleepQuietly(long mills) {
    try {
      Thread.sleep(mills);
    } catch (Exception ignore) {
      //
    }
  }

}
