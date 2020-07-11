package com.fun.jzoffer;

import com.fun.jzoffer.model.ListNode;
import org.junit.Test;

/**
 * @author xulujun 2020/07/11.
 */
public class ListNodeTest {

  @Test
  public void test() throws Exception {
    ListNode<Integer> listNode = ListNode.of(1, 3, 4);
    System.out.println(listNode.toString());
  }

}
