package com.fun.jzoffer;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * @author xulujun 2021/01/27.
 */
public class AllZh2 {

  @Test
  public void test() throws Exception {
    //Integer[] nums = new Integer[]{1, 2, 3};
    //Integer[] nums = new Integer[]{1, 2};
    Integer[] nums = new Integer[]{1, 2, 3, 4, 5};
    List<List<Integer>> zhList = new Solution().runTask(nums);
    System.out.println(JSON.toJSONString(zhList));
  }

  private class Solution {

    public List<List<Integer>> runTask(Integer[] nums) {
      if (nums == null || nums.length == 0) {
        return new ArrayList<>(0);
      }
      List<Integer> sourceList = Arrays.asList(nums);
      List<List<Integer>> resultList = new ArrayList<>();
      doRunTask(nums.length, 0, sourceList, resultList);
      return resultList;
    }

    private void doRunTask(int length, int first, List<Integer> zh,
        List<List<Integer>> zhList) {
      if (first == length) {
        zhList.add(new ArrayList<>(zh));
      }
      for (int i = first; i < length; i++) {
        swap(zh, i, first);
        doRunTask(length, first + 1, zh, zhList);
        swap(zh, i, first);
      }
    }

    public <T> void swap(List<T> list, int i, int j) {
      final List<T> l = list;
      l.set(i, l.set(j, l.get(i)));
    }

  }


}
