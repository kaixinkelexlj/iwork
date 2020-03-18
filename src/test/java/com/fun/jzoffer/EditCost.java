package com.fun.jzoffer;

import org.junit.Test;

/**
 * @author xulujun 2020/03/16.
 *
 * 两个字符串的最小编辑代价：p230
 * 给定两个字符串str1和str2，再给定三个整数ic，dc，rc，分别代表插入、删除、替换的代码，求str1编辑成str2的最小代价
 *
 * str1="abc",str2="adc",ic=5,dc=3,rc=2，b换成d代价最小，所以返回2
 * str1="abc"，str2="adc"，ic=5，dc=3，rc=100，先删除b再插入d代价最小，返回8
 * str1="abc",str2="abc",不用编辑，返回0
 */
public class EditCost {

  /**
   * 使用经典动态规划方法，构建二维矩阵，d[i][j]表示将str1[0..i]编辑成str2[0..j]的最小编辑代价
   */
  public int getEditCost(String str1, String str2, int ic, int dc, int rc) {
    if (str1 == null || str2 == null) {
      return 0;
    }
    char[] source = str1.toCharArray();
    char[] target = str2.toCharArray();
    // 横向是目标，第一个char是''
    // 纵向是源，第一个char是''
    int rowLength = source.length + 1;
    int colLength = target.length + 1;
    int[][] cost = new int[rowLength][colLength];
    // 从空字符串到各个target都是插入
    for (int col = 0; col < colLength; col++) {
      cost[0][col] = ic * col;
    }
    for (int row = 0; row < rowLength; row++) {
      cost[row][0] = dc * row;
    }
    // cost[row][col]
    for (int row = 1; row < rowLength; row++) {
      for (int col = 1; col < colLength; col++) {
        if (source[row - 1] == target[col - 1]) {
          cost[row][col] = cost[row - 1][col - 1];
        } else {
          cost[row][col] = cost[row - 1][col - 1] + rc;
        }
        cost[row][col] = Math.min(cost[row][col], cost[row][col - 1] + ic);
        cost[row][col] = Math.min(cost[row][col], cost[row - 1][col] + dc);
      }
    }
    return cost[rowLength - 1][colLength - 1];
  }

  @Test
  public void test() throws Exception {
    System.out.println(getEditCost("abc", "adc", 5, 3, 2));
    System.out.println(getEditCost("abc", "adc", 5, 3, 100));
    System.out.println(getEditCost("abc", "abc", 5, 3, 100));
    System.out.println(getEditCost("abc", "", 5, 3, 100));
  }

}
