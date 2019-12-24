package com.fun.jzoffer;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun 2019/12/24.
 */
public class QuickSort {

  public static void sort(int start, int end, List<Integer> dataList) {
    if (start >= end) {
      return;
    }
    int pivot = (start + end) >> 1;
    sepByPivot(start, end, pivot, dataList);
    sort(start, pivot - 1, dataList);
    sort(pivot + 1, end, dataList);
  }

  private static void sepByPivot(int start, int end, int pivot, List<Integer> dataList) {
    int cmpValue = dataList.get(pivot);
    dataList.set(pivot, dataList.get(end));
    while (start != end) {
      while (start < end && dataList.get(start) < cmpValue) {
        start++;
      }
      if (start < end) {
        dataList.set(end, dataList.get(start));
        end--;
      }
      while (start < end && dataList.get(end) >= cmpValue) {
        end--;
      }
      if (start < end) {
        dataList.set(start, dataList.get(end));
        start++;
      }
    }
    dataList.set(start, cmpValue);
  }

  @Test
  public void test() throws Exception {
    List<Integer> dataList = Arrays.asList(1, 3, -2, 4, 100, 9);
    sort(0, dataList.size() - 1, dataList);
    System.out.println(StringUtils.join(dataList, ","));
  }

}
