package com.fun.jzoffer;

import java.util.Stack;

import org.junit.Test;

import com.fun.jzoffer.model.IntListNode;

/**
 * @author xulujun 2020/06/05.
 */
public class ReverseLinkListNew {

    private Stack<IntListNode> stack = new Stack<>();

    @Test
    public void test() throws Exception {
        IntListNode head = new IntListNode(1);
        head.next(new IntListNode(2))
                .next(new IntListNode(3))
                .next(new IntListNode(4));
        // IntListNode newHead = reverseByStack(head);
        // IntListNode newHead = reverse(head);
        IntListNode newHead = reverseRecursive(head);
        printLinkList(newHead);
    }

    private IntListNode reverseRecursive(IntListNode head) {
        if (!head.hasNext()) {
            return head;
        }
        IntListNode newHead = new IntListNode(-1);
        doReverseRecursive(head, newHead);
        head.next(null);
        return newHead;
    }

    private IntListNode doReverseRecursive(IntListNode node, IntListNode newHead) {
        if (!node.hasNext()) {
            newHead.setVal(node.getVal());
            return node;
        }
        IntListNode next = doReverseRecursive((IntListNode) node.next(), newHead);
        if (!newHead.hasNext()) {
            newHead.next(node);
        }
        next.next(node);
        return node;
    }

    private IntListNode reverse(IntListNode head) {
        if (head == null || !head.hasNext()) {
            return head;
        }
        IntListNode tmp;
        IntListNode prev = null;
        IntListNode cur = head;
        while (cur.hasNext()) {
            tmp = (IntListNode) cur.next(); // tmp2 cur1 prev:null
            cur.next(prev); // 1->null
            prev = cur;
            cur = tmp; // cur2,prev1
        }
        cur.next(prev);
        return cur;
    }

    private IntListNode reverseByStack(IntListNode node) {

        if (node == null || !node.hasNext()) {
            return node;
        }
        stack.push(node);
        IntListNode tmp;
        while (node.hasNext()) {
            tmp = (IntListNode) node.next();
            stack.push(tmp);
            node = tmp;
        }
        IntListNode newHead = stack.pop();
        IntListNode prev = newHead;
        // stack head
        // prev.next -> tmp
        // tmp -> prev
        while (!stack.empty()) {
            tmp = stack.pop();
            prev.next(tmp);
            prev = tmp;
        }
        prev.next(null);
        return newHead;
    }

    private void printLinkList(IntListNode newHead) {
        for (IntListNode item = newHead; item != null; item = (IntListNode) item.next()) {
            System.out.println(item.toString());
        }
    }


}
