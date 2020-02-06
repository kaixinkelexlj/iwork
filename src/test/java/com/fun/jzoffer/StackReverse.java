package com.fun.jzoffer;

import java.util.Stack;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author xulujun 2020/02/06.
 * 逆序一个栈，仅用递归
 */
public class StackReverse {

  @Test
  public void test() throws Exception {
    Stack<Integer> stack = new Stack<>();

    // 逆序打印值，for fun，和本题无关
    IntStream.rangeClosed(1, 5).forEach(stack::push);
    reversePrint(stack);
    System.out.println("==");
    stack = new Stack<>();
    IntStream.rangeClosed(1, 5).forEach(stack::push);
    //stack = reverseStack(stack);
    reverseStack2(stack);
    while (!stack.isEmpty()) {
      System.out.println(stack.pop());
    }
  }

  private void reversePrint(Stack<Integer> stack) {
    Integer val = stack.pop();
    if (!stack.isEmpty()) {
      reversePrint(stack);
    }
    System.out.println(val);
  }


  /**
   * 笨办法
   * @param stack
   * @return
   */
  private Stack<Integer> reverseStack(Stack<Integer> stack) {
    if (stack == null || stack.isEmpty()) {
      return stack;
    }
    Stack<Integer> resultStack = new Stack<>();
    while (!stack.isEmpty()) {
      resultStack.push(stack.pop());
    }
    return resultStack;
  }

  /**
   *
   * @param stack
   */
  private void reverseStackRecursive(Stack<Integer> stack) {
    if (stack == null || stack.isEmpty()) {
      return;
    }
    int bottom = getAndRemoveBottom(stack);
    reverseStackRecursive(stack);
    stack.push(bottom);
  }

  private Integer getAndRemoveBottom(Stack<Integer> stack) {
    Integer val = stack.pop();
    if (stack.isEmpty()) {
      return val;
    }
    Integer bottomValue = getAndRemoveBottom(stack);
    stack.push(val);
    return bottomValue;
  }

}
