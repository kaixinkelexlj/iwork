package com.fun.algorithm;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xulujun 2023/04/02.
 */
public class Hanoi {

  public static void hanoi(AtomicInteger counter, int n, String a, String b, String c) {
    if (n == 1) {
      counter.addAndGet(1);
      System.out.println("第" + counter + "步:" + a + "-->" + c);
    } else {
      hanoi(counter, n - 1, a, c, b);
      hanoi(counter, 1, a, b, c);
      hanoi(counter, n - 1, b, a, c);
    }
  }

  public static void main(String[] args) {
    // hanoi(1, "x", "y", "z");
    // int n = new Scanner(System.in).nextInt();
    String input;
    Scanner scanner;
    do {
      System.out.println("请输入：");
      scanner = new Scanner(System.in);
      input = scanner.next();
      if ("exit".equalsIgnoreCase(input)) {
        break;
      }
      int n = Integer.parseInt(input);
      System.out.println("计算需要" + Math.round((Math.pow(2, n) - 1)) + "步");
      hanoi(new AtomicInteger(0), n, "a", "b", "c");
    }
    while (scanner.hasNext());
  }

}
