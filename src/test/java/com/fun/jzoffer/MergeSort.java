package com.fun.jzoffer;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun 2020/05/28.
 */
public class MergeSort {

  public int[] mergeSort(int[] array) {
    if (array == null || array.length == 0) {
      return array;
    }
    return doSort(array, 0, array.length - 1);
  }

  private int[] doSort(int[] source, int left, int right) {
    if (left >= right) {
      return new int[]{source[left]};
    }
    int sep = (left + right) >> 1;
    int[] arrLeft = doSort(source, left, sep);
    int[] arrRight = doSort(source, sep + 1, right);
    return mergeArray(arrLeft, arrRight);
  }

  private int[] mergeArray(int[] arrLeft, int[] arrRight) {
    int[] result = new int[arrLeft.length + arrRight.length];
    int leftIndex = 0;
    int rightIndex = 0;
    int resultIndex = 0;
    while (leftIndex < arrLeft.length && rightIndex < arrRight.length) {
      if (arrLeft[leftIndex] < arrRight[rightIndex]) {
        result[resultIndex++] = arrLeft[leftIndex++];
      } else {
        result[resultIndex++] = arrRight[rightIndex++];
      }
    }
    if (leftIndex < arrLeft.length) {
      System.arraycopy(arrLeft, leftIndex, result, resultIndex, arrLeft.length - leftIndex);
    }
    if (rightIndex < arrRight.length) {
      System.arraycopy(arrRight, rightIndex, result, resultIndex, arrRight.length - rightIndex);
    }
    return result;
  }


  @Test
  public void test() throws Exception {
    int[] array = {12, 4, 28, 104, 2, 3, 0, 123};
    //Integer[] array = {12, 4, 28}
    int[] resultArr = mergeSort(array);
    System.out.println(StringUtils.join(resultArr, ','));
  }

}
