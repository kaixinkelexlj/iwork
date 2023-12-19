package com.fun.jzoffer;

import com.fun.jzoffer.model.TreeNode;
import java.util.Stack;
import org.junit.Test;

/**
 * @author xulujun 2019/12/13.
 *
 * 二叉树Z字打印
 *     1
 *    / \
 *    3  2
 *   / \ / \
 *   4 5 6 7
 */
public class TreeZPrint {

  public static void zPrint(TreeNode treeNode) {
    if (treeNode == null) {
      return;
    }
    boolean leftToRight = true;
    Stack<TreeNode> stackLR = new Stack<>();
    Stack<TreeNode> stackRL = new Stack<>();
    stackLR.push(treeNode);
    TreeNode node;
    while(!stackLR.isEmpty() || !stackRL.isEmpty()){
      if(leftToRight){
        node = stackLR.pop();
        visitNode(node);
        if(node.getLeft() != null){
          stackRL.push(node.getLeft());
        }
        if(node.getRight() != null){
          stackRL.push(node.getRight());
        }
      }else{
        node = stackRL.pop();
        visitNode(node);
        if(node.getRight() != null){
          stackLR.push(node.getRight());
        }
        if(node.getLeft() != null){
          stackLR.push(node.getLeft());
        }
      }
      if(leftToRight && stackLR.isEmpty()){
        leftToRight = false;
      }
      if(!leftToRight && stackRL.isEmpty()){
        leftToRight = true;
      }
    }

  }

  private static void visitNode(TreeNode node) {
    System.out.println(node.getValue());
  }

  @Test
  public void test() throws Exception {
    TreeNode node12 = new TreeNode(null, null, 12);
    TreeNode node11 = new TreeNode(null, null, 11);
    TreeNode node10 = new TreeNode(null, null, 10);
    TreeNode node9 = new TreeNode(null, null, 9);
    TreeNode node8 = new TreeNode(null, null, 8);
    TreeNode node4 = new TreeNode(node12, node11, 4);
    TreeNode node5 = new TreeNode(null, null, 5);
    TreeNode node6 = new TreeNode(node10, null, 6);
    TreeNode node7 = new TreeNode(node9, node8, 7);
    TreeNode node3 = new TreeNode(node4, node5, 3);
    TreeNode node2 = new TreeNode(node6, node7, 2);
    TreeNode root = new TreeNode(node3, node2, 1);
    zPrint(root);
  }


}
