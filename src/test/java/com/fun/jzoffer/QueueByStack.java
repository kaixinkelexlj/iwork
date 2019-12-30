package com.fun.jzoffer;

import java.util.Stack;
import org.junit.Test;

/**
 * @author xulujun 2019/12/30.
 */
public class QueueByStack {

  @Test
  public void test() throws Exception {
    MyQueue<Integer> queue = new MyQueue<>();
    queue.enQueue(1);
    queue.enQueue(2);
    queue.enQueue(3);
    System.out.println(queue.deQueue());
    System.out.println(queue.deQueue());
    System.out.println(queue.deQueue());
    System.out.println(queue.deQueue());
    System.out.println(queue.deQueue());
    queue.enQueue(4);
    queue.enQueue(5);
    System.out.println(queue.deQueue());
    System.out.println(queue.deQueue());
    System.out.println(queue.deQueue());
    System.out.println(queue.deQueue());
    System.out.println(queue.deQueue());
  }


  public static class MyQueue<E> {

    private Stack<E> input;
    private Stack<E> output;

    public MyQueue() {
      this.input = new Stack<>();
      this.output = new Stack<>();
    }


    public void clear() {
      input.clear();
      output.clear();
    }


    public boolean enQueue(E e) {
      input.push(e);
      return false;
    }


    public E deQueue() {
      if (!output.isEmpty()) {
        return output.pop();
      }
      if (input.isEmpty()) {
        return null;
      }
      while (!input.isEmpty()) {
        output.push(input.pop());
      }
      return output.pop();
    }

  }

}
