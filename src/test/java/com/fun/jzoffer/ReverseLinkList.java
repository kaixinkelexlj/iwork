package com.fun.jzoffer;

import com.fun.jzoffer.model.IntListNode;
import org.junit.Test;

/**
 * @author xulujun 2020/06/05.
 */
public class ReverseLinkList {


  @Test
  @SuppressWarnings("unchecked")
  public void test() throws Exception {
    IntListNode head = new IntListNode(1);
    head.next(new IntListNode(2))
        .next(new IntListNode(3))
        .next(new IntListNode(4));
    System.out.println(head.toString());
    // printNodeList(reverse(head));
    System.out.println(reverseRecursive(head).toString());
  }

  @SuppressWarnings("unchecked")
  private IntListNode reverse(IntListNode head) {

    if (head == null || !head.hasNext()) {
      return head;
    }
    IntListNode current = head;
    IntListNode prev = null;
    IntListNode temp;
    while (current != null) {
      temp = (IntListNode) current.next();
      current.next(prev);
      prev = current;
      current = temp;
    }
    return prev;

  }

  @SuppressWarnings("unchecked")
  private IntListNode reverseRecursive(IntListNode node) {
    if (node == null || node.next() == null) {
      return node;
    }
    IntListNode head = reverseRecursive((IntListNode)node.next());
    node.next().next(node);
    node.next(null);
    return head;
  }


  private void printNodeList(IntListNode head) {
    if (head == null) {
      return;
    }
    for (IntListNode node = head; node != null; ) {
      System.out.println(node.getVal());
      node = (IntListNode)node.next();
    }

  }


}
