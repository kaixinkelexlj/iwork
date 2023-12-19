package com.fun.jzoffer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xulujun 2019/12/13.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {

  private TreeNode left;
  private TreeNode right;
  private int value;


}
