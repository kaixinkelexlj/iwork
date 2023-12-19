package com.fun.jzoffer.model;

import com.google.common.base.Preconditions;

/**
 * @author xulujun 2020/07/11.
 */
public class ListNode2 {

  public int val;
  public ListNode2 next;

  public ListNode2(int x) {
    val = x;
  }

  public static ListNode2 of(int... values) {
    Preconditions.checkArgument(values != null && values.length > 0);
    ListNode2 head = new ListNode2(values[0]);
    if (values.length < 2) {
      return head;
    }
    ListNode2 node = head;
    for (int i = 1; i < values.length; i++) {
      node.next = new ListNode2(values[i]);
      node = node.next;
    }
    return head;
  }

  @Override
  public String toString() {
    return toString("->");
  }

  public String toString(String seperator) {
    StringBuilder output = new StringBuilder();
    ListNode2 node = this;
    do {
      output.append(node.val).append(seperator);
      node = node.next;
    } while (node != null && node.next != null);
    if (node != null) {
      output.append(node.val);
    }
    return output.toString();
  }

}
