package com.fun.jzoffer;

import org.junit.Test;

/**
 * @author xulujun 2020/03/20.
 *
 * 矩阵最小路径和
 *
 * 给定一个矩阵，求从左上到右下，最小路径和
 *
 */
public class MinPathSum {

  public int calMinPathSum(int[][] costMatrix) {
    if (costMatrix == null || costMatrix.length == 0 || costMatrix[0].length == 0) {
      return 0;
    }
    int rowLength = costMatrix.length;
    int colLength = costMatrix[0].length;
    int[][] sumMetrix = new int[rowLength][colLength];
    sumMetrix[0][0] = costMatrix[0][0];
    // 初始化第一行
    for (int col = 1; col < colLength; col++) {
      sumMetrix[0][col] = sumMetrix[0][col - 1] + costMatrix[0][col];
    }
    // 初始化第一列
    for (int row = 1; row < rowLength; row++) {
      sumMetrix[row][0] = sumMetrix[row - 1][0] + costMatrix[row][0];
    }
    for (int row = 1; row < rowLength; row++) {
      for (int col = 1; col < colLength; col++) {
        sumMetrix[row][col] =
            Math.min(sumMetrix[row - 1][col], sumMetrix[row][col - 1]) + costMatrix[row][col];
      }
    }
    return sumMetrix[rowLength - 1][colLength - 1];
  }


  @Test
  public void test() throws Exception {
    int[][] matrix = {
        {1, 3, 5, 9},
        {8, 1, 3, 4},
        {5, 0, 6, 1},
        {8, 8, 4, 0}
    };
    System.out.println(calMinPathSum(matrix));
  }


}
