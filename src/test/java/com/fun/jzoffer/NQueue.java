package com.fun.jzoffer;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun 2020/03/12.
 *
 * N皇后问题是一个经典的问题，在一个N*N的棋盘上放置N个皇后，每行一个并使其不能互相攻击（同一行、同一列、同一斜线上的皇后都会自动攻击）
 * 有多少种摆法（同一斜线指(x1, y1), (x2, y2)两个点，|x2 - x1| == |y2 - y1|）
 *
 * n = 1返回1
 * n = 2或者3，返回0
 * n = 8,返回92
 */
public class NQueue {


  public int nQueue(int n) {
    System.out.println(String.format("===%s==", n + "queue pos start"));
    int hits = doPutQueue(0, n, new Integer[n]);
    System.out.println(String.format("===%s==", n + "queue pos end"));
    return hits;
  }

  public int doPutQueue(int row, int n, Integer[] queuePos) {
    if (row == n) {
      System.out.println(StringUtils.join(queuePos, ","));
      return 1;
    }
    int hit = 0;
    for (int col = 0; col < n; col++) {
      if (canPut(row, col, queuePos)) {
        queuePos[row] = col;
        hit += doPutQueue(row + 1, n, queuePos);
      }
    }
    return hit;
  }

  private boolean canPut(int row, int col, Integer[] queuePos) {
    for (int i = 0; i < row; i++) {
      int queue = queuePos[i];
      if (queue == col) {
        return false;
      }
      if (Math.abs(row - i) == Math.abs(col - queue)) {
        return false;
      }
    }
    return true;
  }

  @Test
  public void test() throws Exception {
    System.out.println(nQueue(1));
    System.out.println(nQueue(2));
    System.out.println(nQueue(3));
    System.out.println(nQueue(4));
    System.out.println(nQueue(8));
  }

}
