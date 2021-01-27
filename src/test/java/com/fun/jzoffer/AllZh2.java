package com.fun.jzoffer;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * 没有重复数字的数组，全排列.
 * @author xulujun 2021/01/27.
 */
public class AllZh2 {

  @Test
  public void test() throws Exception {
    int[] nums = new int[]{1, 2, 3};
    List<List<Integer>> zhList = new Solution().runTask(nums);
    System.out.println(JSON.toJSONString(zhList));
  }

  private class Solution {

    public List<List<Integer>> runTask(int[] nums) {
      if (nums == null || nums.length == 0) {
        return new ArrayList<>(0);
      }
      List<Integer> seed = Arrays.stream(nums).boxed().collect(Collectors.toList());
      List<List<Integer>> resultList = new ArrayList<>();
      doRunTask(nums.length, 0, seed, resultList);
      return resultList;
    }

    private void doRunTask(int length, int first, List<Integer> seed,
        List<List<Integer>> resultList) {
      if (first == length) {
        resultList.add(new ArrayList<>(seed));
        return;
      }
      for (int i = first; i < length; i++) {
        if (i != first) {
          swap(seed, i, first);
        }
        doRunTask(length, first + 1, seed, resultList);
        if (i != first) {
          swap(seed, i, first);
        }
      }
    }

    public <T> void swap(List<T> list, int i, int j) {
      final List<T> l = list;
      l.set(i, l.set(j, l.get(i)));
    }

  }


}
