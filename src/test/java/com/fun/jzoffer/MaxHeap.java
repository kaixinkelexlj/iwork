package com.fun.jzoffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun 2020/01/08.
 *
 *
 * 大顶堆.
 *
 * https://www.cnblogs.com/sxkgeek/p/9662491.html(代码有错误，代码里有调整)
 * https://blog.csdn.net/simatongming/article/details/72307280（正确）
 * https://www.cnblogs.com/chengxiao/p/6129630.html（堆排序）
 *
 * length / 2 - 1 很关键
 *
 */
public class MaxHeap {

  /* 把根节点跟最后一个元素交换位置，调整剩下的n-1个节点，即可排好序 */
  /* 对一个大顶堆排序 */
  public void heapSort(List<Integer> list) {
    buildMaxTopHeap(list);
    for (int i = list.size() - 1; i > 0; i--) {
      swap(list, 0, i);
      adjustHeap(list, 0, i);
    }
  }

  /**
   * 构建大顶堆
   * @param heap
   */
  private void buildMaxTopHeap(List<Integer> heap) {

    if (heap == null || heap.size() == 0) {
      return;
    }
    for (int i = heap.size() / 2 - 1; i >= 0; i--) {
      adjustHeap(heap, i, heap.size());
      System.out.println(StringUtils.join(heap, ","));
    }
  }

  private void adjustHeap(List<Integer> heap, int i, int length) {
    int child;
    for (; i < length / 2; ) {
      child = 2 * i + 1;
      // 左右子节点比较大小
      if (child + 1 <= length - 1 && heap.get(child + 1) > heap.get(child)) {
        child += 1;
      }
      if (heap.get(i) < heap.get(child)) {
        swap(heap, i, child);
        /*交换后,以child为根的子树不一定满足堆定义,所以从child处开始调整*/
        i = child;
      } else {
        break;
      }
    }
  }

  private static void swap(List<Integer> heap, int a, int b) {
    //临时存储child位置的值
    int temp = heap.get(a);
    //把index的值赋给child的位置
    heap.set(a, heap.get(b));
    //把原来的child位置的数值赋值给index位置
    heap.set(b, temp);
  }

  @Test
  public void test() throws Exception {
    // 大顶堆 93,72,48,53,45,30,18,36,15,35
    List<Integer> list = Arrays.asList(45, 36, 18, 53, 72, 30, 48, 93, 15, 35);
    List<Integer> listCmp = new ArrayList<>(list);
    System.out.println(StringUtils.join(list, ","));
    heapSort(list);
    System.out.println("=== sorted == ");
    System.out.println(StringUtils.join(list, ","));
    Collections.sort(listCmp);
    System.out.println(StringUtils.join(listCmp, ","));
    // 15,18,30,36,45,48,53,72,93


    list = Arrays.asList(45, 36, 18, 53, 72, 30, 48, 93, 35);
    listCmp = new ArrayList<>(list);
    System.out.println(StringUtils.join(list, ","));
    heapSort(list);
    System.out.println("=== sorted == ");
    System.out.println(StringUtils.join(list, ","));
    Collections.sort(listCmp);
    System.out.println(StringUtils.join(listCmp, ","));
  }

}
