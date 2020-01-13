package com.fun.jzoffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun 2020/01/02.
 *
 * https://blog.csdn.net/gatieme/article/details/51277505
 *
 */
public class MinK {

  @Test
  public void test() throws Exception {

    /*例如输入4,5,1,6,2,7,3,8这8个数字，
    则最小的4个数字是1,2,3,4,。*/
    List<Integer> source = Arrays.asList(4, 5, 1, 6, 2, 7, 3, 8);
    List<Integer> resultList = listMin(source, 4);
    System.out.println(StringUtils.join(resultList, ","));

  }

  private List<Integer> listMin(List<Integer> list, int k) {
    if (list == null || list.size() == 0) {
      return new ArrayList<>(0);
    }
    if (k <= 0) {
      return new ArrayList<>();
    }
    if (list.size() <= k) {
      return new ArrayList<>(list);
    }
    List<Integer> heap = list.subList(0, k);
    buildMaxHeap(heap); // 构建大顶堆
    Integer val;
    for (int i = k; i < list.size(); i++) {
      val = list.get(i);
      if (heap.get(0) < val) {
        heap.set(0, val);
        adjustHeap(heap, 0, k);
      }
    }
    return heap;
  }

  private void adjustHeap(List<Integer> heap, int i, int length) {
    int child;
    for (; i <= length / 2 - 1; ) {
      child = 2 * i + 1;
      if (child + 1 <= length && heap.get(child + 1) > heap.get(child)) {
        child += 1;
      }
      if (heap.get(i) < heap.get(child)) {
        swap(heap, i, child);
        i = child;
      } else {
        break;
      }
    }
  }

  private void swap(List<Integer> heap, int i, int child) {
    Integer tmp = heap.get(i);
    heap.set(i, heap.get(child));
    heap.set(child, tmp);
  }

  private void buildMaxHeap(List<Integer> heap) {
    for (int i = heap.size() / 2 - 1; i >= 0; i--) {
      adjustHeap(heap, i, heap.size());
    }
  }

}
