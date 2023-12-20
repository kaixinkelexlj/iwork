package com.fun.jzoffer.model;

import com.google.common.base.Preconditions;

/**
 * @author xulujun 2020/06/05.
 */
public class ListNode<T> {

    protected T val;
    protected ListNode<T> next;

    public static <T> ListNode<T> of(T... values) {
        Preconditions.checkArgument(values != null && values.length > 0);
        ListNode<T> head = new ListNode<>(values[0]);
        if (values.length < 2) {
            return head;
        }
        ListNode<T> node = head;
        for (int i = 1; i < values.length; i++) {
            node.next(new ListNode<>(values[i]));
            node = node.next;
        }
        return head;
    }

    public ListNode() {
    }

    public ListNode(T val) {
        this.val = val;
    }

    public ListNode<T> next(ListNode<T> listNode) {
        this.next = listNode;
        return this.next;
    }

    public ListNode<T> next() {
        return next;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public boolean hasNext() {
        return next != null;
    }

    @Override
    public String toString() {
        return val + "->" + (next == null ? "" : next.val);
    }

    public String toString(String separator) {
        StringBuilder output = new StringBuilder();
        ListNode<T> node = this;
        do {
            output.append(node.val).append(separator);
            node = node.next;
        } while (node != null && node.hasNext());
        if (node != null) {
            output.append(node.val);
        }
        return output.toString();
    }

}
