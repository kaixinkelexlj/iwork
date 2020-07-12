package com.fun.jzoffer;

import com.fun.jzoffer.model.ListNode2;
import org.junit.Test;

/**
 * @author xulujun 2020/07/11.
 *
 * https://leetcode-cn.com/problems/reverse-nodes-in-k-group
 *
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 *
 * k 是一个正整数，它的值小于或等于链表的长度。
 *
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 *  
 *
 * 示例：
 *
 * 给你这个链表：1->2->3->4->5
 *
 * 当 k = 2 时，应当返回: 2->1->4->3->5
 *
 * 当 k = 3 时，应当返回: 3->2->1->4->5
 *
 *  
 *
 * 说明：
 *
 * 你的算法只能使用常数的额外空间。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-nodes-in-k-group
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *  给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 *
 * k 是一个正整数，它的值小于或等于链表的长度。
 *
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 *  
 *
 * 示例：
 *
 * 给你这个链表：1->2->3->4->5
 *
 * 当 k = 2 时，应当返回: 2->1->4->3->5
 *
 * 当 k = 3 时，应当返回: 3->2->1->4->5
 *
 *  
 *
 * 说明：
 *
 * 你的算法只能使用常数的额外空间。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-nodes-in-k-group
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class KGroupReverseLinkedList {


  @Test
  public void test() throws Exception {
    ListNode2 head = ListNode2.of(1, 2, 3, 4, 5);
    ListNode2 result = kReverse(head, 2);
    System.out.println(result.toString());

    head = ListNode2.of(1, 2, 3, 4, 5);
    result = kReverse(head, 3);
    System.out.println(result.toString());
  }

  @Test
  public void testReverse() throws Exception {
    ListNode2 head = ListNode2.of(1, 2, 3, 4);
    HeadAndTail headAndTail = reverseList(head, head.next.next);
    System.out.println(headAndTail.head.toString());
    System.out.println(headAndTail.tail.toString());
  }

  private ListNode2 kReverse(ListNode2 head, int k) {
    if (head == null || head.next == null) {
      return head;
    }

    // 1->2->3->4->5
    // 21 -> 43 -> 5
    ListNode2 groupHead = head;
    ListNode2 groupTail = head;
    ListNode2 prevGroupTail = null;
    ListNode2 groupTailNext;
    ListNode2 resultHead = head;
    HeadAndTail reverseResult;
    while (groupTail.next != null) {
      for (int i = 0; i < k - 1; i++) {
        if (groupTail.next == null) {
          return resultHead;
        }
        groupTail = groupTail.next;
      }
      // 记录翻转前groupTail的next
      groupTailNext = groupTail.next;
      reverseResult = reverseList(groupHead, groupTail);
      // 只发生一次
      if (resultHead == head) {
        resultHead = reverseResult.head;
      }
      // prev.next连接result.head
      if (prevGroupTail != null) {
        prevGroupTail.next = reverseResult.head;
      }
      reverseResult.tail.next = groupTailNext;
      prevGroupTail = reverseResult.tail;
      groupHead = reverseResult.tail.next;
      groupTail = groupHead;
    }
    return resultHead;
  }

  // 0 -> (1 -> 2 -> 3) -> 4
  // 4 <- (1 <- 2 <- 3) <- 0
  private HeadAndTail reverseList(ListNode2 head, ListNode2 tail) {
    if (head == tail) {
      return new HeadAndTail(head, tail);
    }
    ListNode2 current = head;
    ListNode2 prev = tail.next;
    ListNode2 tmp;
    // 1 -> 2 -> 3
    while (current != tail) {
      tmp = current.next;
      current.next = prev;
      prev = current;
      current = tmp;
    }
    current.next = prev;
    return new HeadAndTail(current, head);
  }

  private class HeadAndTail {

    public ListNode2 head;
    public ListNode2 tail;

    public HeadAndTail(ListNode2 head, ListNode2 tail) {
      this.head = head;
      this.tail = tail;
    }
  }

}
