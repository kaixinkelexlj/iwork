package com.fun.jzoffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun 2020/01/13.
 *
 * 窗口最大值
 *
 * 43543367， 窗口大小为3，输出结果555467
 */
public class WindowMax {

  @Test
  public void test() throws Exception {
    List<Integer> data = Arrays.asList(4, 3, 5, 4, 3, 3, 6, 7);
    List<Integer> maxList = listWindowMax(data, 3);
    System.out.println(StringUtils.join(maxList, ","));
  }

  @Test
  public void testQueue() throws Exception {
    FixWithMaxQueue<Integer> queue = new FixWithMaxQueue<>(3);
    for (Integer val : Arrays.asList(4, 3, 5, 4, 3, 3, 6, 7)) {
      queue.add(val);
      System.out.println(queue.getMaxValue());
    }
    LinkedList<Integer> queue2 = new LinkedList<>(Arrays.asList(4, 3, 5, 4, 3, 3, 6, 7));
    System.out.println("====== first and last ======");
    System.out.println(queue2.peekFirst());
    System.out.println(queue2.peekLast());
  }

  public List<Integer> listWindowMax(List<Integer> data, int k) {
    if (data == null || data.size() == 0) {
      return new ArrayList<>(0);
    }
    if (k <= 0) {
      return new ArrayList<>(0);
    }
    List<Integer> resultList = new ArrayList<>(k);
    FixWithMaxQueue<Integer> queue = new FixWithMaxQueue<>(k);
    int i = 0;
    for (Integer item : data) {
      queue.add(item);
      if (i >= k - 1) {
        resultList.add(queue.getMaxValue());
        System.out.println(StringUtils.join(queue.getListSnapshot(), "#"));
      }
      i++;
    }
    System.out.println(StringUtils.join(resultList, ","));
    return resultList;
  }

  // 暂时不考虑多线程情况
  public static class FixWithMaxQueue<T extends Comparable> {

    private LinkedList<T> list;
    private Stack<T> stack;
    private int maxLength;

    public FixWithMaxQueue(int maxLength) {
      this.maxLength = maxLength;
      this.stack = new Stack<>();
      this.list = new LinkedList<>();
    }

    public void add(T item) {
      while (list.size() >= maxLength) {
        popValue();
      }
      pushValue(item);
    }

    public T getMaxValue() {
      return stack.peek();
    }

    public List<T> getListSnapshot() {
      return new ArrayList<>(list);
    }

    @SuppressWarnings("unchecked")
    private void pushValue(T item) {
      list.add(item);
      if (stack.isEmpty() || item.compareTo(stack.peek()) >= 0) {
        stack.push(item);
      }
    }

    private void popValue() {
      T val = this.list.pollFirst();
      if (val.equals(stack.peek())) {
        stack.pop();
      }
    }

  }
}
