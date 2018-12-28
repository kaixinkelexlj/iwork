package com.work.job.sffun;

import java.util.Arrays;
import org.junit.Test;

/**
 * @author xulujun
 * @date 2018/12/28
 */
public class PrintNNumsTest {

  @Test
  public void test1() throws Exception {
    printNNumbers(3);
  }

  @Test
  public void test2() throws Exception {
    printNNumbersRecursion(2);
  }

  /**
   * 输入数字n，按顺序打印出从1最大的n位十进制数。比如输入3，则打印出1、2、3 一直到最大的3位数即999。
   */
  public static void printNNumbers(int n) throws Exception {
    if (n <= 0) {
      return;
    }
    int max = calMax(n);
    for (int i = 1; i <= max; i++) {
      System.out.println(i);
    }
  }

  public static void printNNumbersRecursion(int n) throws Exception {
    setVal(new int[n], 0);
  }

  private static void setVal(int[] arr, int index) {
    if (index >= arr.length) {
      printArray(arr);
      return;
    }
    for (int i = 0; i < 10; i++) {
      arr[index] = i;
      setVal(arr, index + 1);
    }
  }

  private static void printArray(int[] arr) {
    Arrays.stream(arr)
        .forEach(System.out::print);
    System.out.println();
  }

  private static int calMax(int n) {
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = 9;
    }
    int result = 0;
    for (int i = 0; i < arr.length; i++) {
      result += arr[i] * Math.round(Math.pow(10, n - 1 - i));
    }
    System.out.println(result);
    return result;
  }

}
