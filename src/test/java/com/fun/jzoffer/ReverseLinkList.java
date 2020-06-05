package com.fun.jzoffer;

import com.fun.jzoffer.model.ListNode;
import org.junit.Test;

/**
 * @author xulujun 2020/06/05.
 */
public class ReverseLinkList {


  @Test
  public void test() throws Exception {
    ListNode head = new ListNode(1);
    head.next(new ListNode(2))
        .next(new ListNode(3))
        .next(new ListNode(4));
    printNodeList(head);
    printNodeList(reverse(head));
  }

  private ListNode reverse(ListNode head) {

    if (head == null || !head.hasNext()) {
      return head;
    }
    ListNode current = head;
    ListNode prev = null;
    ListNode temp;
    while (current != null) {
      temp = current.next();
      current.next(prev);
      prev = current;
      current = temp;
    }
    return prev;

  }

  private void printNodeList(ListNode head) {
    if (head == null) {
      return;
    }
    for (ListNode node = head; node != null; ) {
      System.out.println(node.getVal());
      node = node.next();
    }

  }


}
