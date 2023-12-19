package com.fun.jzoffer;

import com.fun.jzoffer.model.ListNode;
import java.util.Optional;
import org.junit.Test;

/**
 * @author xulujun 2020/07/11.
 *
 * https://leetcode-cn.com/problems/add-two-numbers
 *
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class DataAdd extends JzOffer {


  public ListNode<Integer> add(ListNode<Integer> leftHead, ListNode<Integer> rightHead) {

    ListNode<Integer> leftNode = leftHead;
    ListNode<Integer> rightNode = rightHead;
    ListNode<Integer> result = new ListNode<>();
    ListNode<Integer> resultHead = result;
    int delta = 0;
    for (; ; ) {
      int leftVal = Optional.ofNullable(leftNode).map(ListNode::getVal).orElse(0);
      int rightVal = Optional.ofNullable(rightNode).map(ListNode::getVal).orElse(0);
      int sum = leftVal + rightVal + delta;
      result.setVal(sum % 10);

      delta = sum >= 10 ? 1 : 0;
      if (leftNode != null) {
        leftNode = leftNode.next();
      }
      if (rightNode != null) {
        rightNode = rightNode.next();
      }
      if (leftNode == null && rightNode == null) {
        if(delta > 0){
          result = result.next(new ListNode<>());
          result.setVal(delta);
        }
        break;
      } else {
        result = result.next(new ListNode<>());
      }
    }
    return resultHead;
  }

  @Test
  public void test() throws Exception {
    ListNode<Integer> left = ListNode.of(2, 4, 3);
    ListNode<Integer> right = ListNode.of(5, 6, 4);
    ListNode<Integer> result = add(left, right);
    testAndPrintout("708".equals(result.toString("")), result.toString());

    left = ListNode.of(5);
    right = ListNode.of(5);
    result = add(left, right);
    testAndPrintout("01".equals(result.toString("")), result.toString());

    left = ListNode.of(1);
    right = ListNode.of(9, 9);
    result = add(left, right);
    testAndPrintout("001".equals(result.toString("")), result.toString());

  }

}
