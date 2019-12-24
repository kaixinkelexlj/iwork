package com.fun.jzoffer;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xulujun 2019/12/17.
 *
 * https://blog.csdn.net/sgbfblog/article/details/8032464
 *
 * 最大连续子数列和一道很经典的算法问题，给定一个数列，其中可能有正数也可能有负数，我们的任务是找出其中连续的一个子数列（不允许空序列），使它们的和尽可能大。我们一起用多种方式，逐步优化解决这个问题。
 *
 *
 * 为了更清晰的理解问题，首先我们先看一组数据：
 * 8
 * -2 6 -1 5 4 -7 2 3
 *
 *
 * 第一行的8是说序列的长度是8，然后第二行有8个数字，即待计算的序列。
 * 对于这个序列，我们的答案应该是14，所选的数列是从第2个数到第5个数，这4个数的和是所有子数列中最大的。
 *
 */
public class SubSequenceSum {

  // 最笨
  public static Integer calMaxSum(List<Integer> dataList) {
    if (dataList == null || dataList.size() == 0) {
      throw new IllegalArgumentException("invalid dataList");
    }
    int currentMax = 0;
    for (int i = 0; i < dataList.size(); i++) {
      int max = dataList.get(i);
      for (int j = i + 1; j < dataList.size(); j++) {
        max += dataList.get(j);
        if (max > currentMax) {
          currentMax = max;
        }
      }
    }
    return currentMax;
  }

  //分治
  public static Integer calMaxSumFz(List<Integer> dataList) {
    if (dataList == null || dataList.size() == 0) {
      throw new IllegalArgumentException("invalid dataList");
    }
    return fzSumMax(0, dataList.size() - 1, dataList);
  }

  /**
   * 还有一种更好的解法，只需要O（N）的时间。因为最大连续子序列和只可能是以位置0～n-1中某个位置结尾。当遍历到第i个元素时，判断在它前面的连续子序列和是否大于0，如果大于0，则以位置i结尾的最大连续子序列和为元素i和前面的连续子序列和相加；否则，则以位置i结尾的最大连续子序列和为元素i
   * result[i] = max(result[i-1], 0) + data[i]
   */
  public static Integer calMaxDynamicProgram(List<Integer> dataList) {
    if (dataList == null || dataList.size() == 0) {
      throw new IllegalArgumentException("invalid dataList");
    }
    int max = dataList.get(0);
    int sum = dataList.get(0);
    for (int i = 1; i < dataList.size(); i++) {
      if (sum > 0) {
        sum = sum + dataList.get(i);
      } else {
        sum = dataList.get(i);
      }
      max = Math.max(max, sum);
    }
    return max;
  }

  private static Integer fzSumMax(int start, int end, List<Integer> dataList) {
    if (start > end) {
      return 0;
    }
    if (start == end) {
      return dataList.get(start);
    }
    int mid = (start + end) >> 1;
    int lMax = 0, rMax = 0;
    int sum = 0;
    for (int i = mid; i >= start; i--) {
      sum += dataList.get(i);
      lMax = Math.max(sum, lMax);
    }
    sum = 0;
    for (int i = mid + 1; i <= end; i++) {
      sum += dataList.get(i);
      rMax = Math.max(sum, rMax);
    }
    return Math.max(lMax + rMax,
        Math.max(fzSumMax(start, mid, dataList), fzSumMax(mid + 1, end, dataList)));
  }


  @Test
  public void test() throws Exception {
    List<Integer> dataList = Arrays.asList(-2, 6, -1, 5, 4, -7, 2, 3);
    Assert.assertEquals("fail", 14, (int) calMaxSum(dataList));
    Assert.assertEquals("fail", 14, (int) calMaxSumFz(dataList));
    Assert.assertEquals("fail", 14, (int) calMaxDynamicProgram(dataList));
  }

}
