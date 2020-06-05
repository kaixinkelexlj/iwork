package com.fun.jzoffer.model;

/**
 * @author xulujun 2020/06/05.
 */
public class ListNode {

  private int val;
  private ListNode next;

  public ListNode(int val) {
    this.val = val;
  }

  public ListNode next(ListNode listNode) {
    this.next = listNode;
    return this.next;
  }

  public ListNode next() {
    return next;
  }

  public int getVal() {
    return val;
  }

  public void setVal(int val) {
    this.val = val;
  }

  public boolean hasNext() {
    return next != null;
  }

}
