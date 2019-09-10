package com.work.job.jvm;

/**
 * @author xulujun
 * @date 2019/02/15
 */
public class JvmTest {

  public static void main(String[] args) throws Exception {
    testStringStoreError();
  }

  // -Xms1M -Xmx16M -XX:+PrintGCDetails -XX:+PrintGCDateStamps
  public static void testStringStoreError() throws Exception {
    String a = "hello world";
    while (true) {
      new String(String.valueOf(System.nanoTime()));
    }
  }

}
