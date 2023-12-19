package com.fun.jzoffer;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author xulujun 2020/03/18.
 *
 * https://baike.baidu.com/item/%E6%B1%89%E8%AF%BA%E5%A1%94/3468295
 *
 * 汉诺塔：给定一个数字n，代表从小到大的n个圆盘，打印从左柱按游戏规则全部移动到右柱的移动轨迹
 *
 * 死记硬背吧。。
 */
public class HannuoTa {

  public void hannuoTa(int n) {
    if (n <= 0) {
      return;
    }
    if (n == 1) {
      move("right");
    }
    LinkedList<Integer> list = IntStream.rangeClosed(1, n).boxed()
        .collect(Collectors.toCollection(LinkedList::new));
    doWork(n, list, new LinkedList<>(), new LinkedList<>());
  }

  private void doWork(int n, LinkedList<Integer> leftList, LinkedList<Integer> midList,
      LinkedList<Integer> rightList) {
    if (rightList.size() == n) {
      return;
    }
    int left = !leftList.isEmpty() ? leftList.peekFirst() : 0;
    int mid = !midList.isEmpty() ? midList.peekFirst() : 0;
  }

  private void move(String path) {
    System.out.println(path);
  }

  public void hanoi(int n) {
    if (n > 0) {
      func(n, "left", "mid", "right");
    }
  }

  private void func(int n, String from, String mid, String to) {
    if (n == 1) {
      System.out.println(String.format("move from %s to %s", from, to));
      return;
    }
    func(n - 1, from, to, mid);
    func(1, from, mid, to);
    func(n - 1, mid, from, to);
  }


  @Test
  public void test() throws Exception {
    hanoi(5);
  }

  @Test
  public void test2() throws Exception {
    LinkedList<Integer> list = IntStream.rangeClosed(1, 100).boxed()
        .collect(Collectors.toCollection(LinkedList::new));
    System.out.println(list.pop());
  }

}
