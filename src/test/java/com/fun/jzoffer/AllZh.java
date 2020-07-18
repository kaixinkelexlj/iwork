package com.fun.jzoffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun 2020/07/17.
 *
 * https://leetcode-cn.com/problems/permutations/
 *
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class AllZh {

  @Test
  public void test() throws Exception {
    List<List<Integer>> result = new Solution().permute(new int[]{1, 2, 3});
    result.forEach(list -> System.out.println(StringUtils.join(list, ",")));
    //new Solution2().permute(new int[]{1, 2, 3});

  }


  class Solution {

    public List<List<Integer>> permute(int[] nums) {
      List<List<Integer>> result = new ArrayList<>();
      doWork(nums, 0, new Integer[nums.length], result);
      return result;
    }

    private void doWork(int[] nums, int pos, Integer[] store,
        List<List<Integer>> result) {
      if (pos == nums.length) {
        result.add(new ArrayList<>(Arrays.asList(store)));
        return;
      }
      for (int num : nums) {
        if (contains(store, pos, num)) {
          continue;
        }
        store[pos] = num;
        doWork(nums, pos + 1, store, result);
      }

    }

    private boolean contains(Integer[] arr, int endIndex, int target) {
      for (int j = 0; j < endIndex; j++) {
        if (arr[j] != null && arr[j] == target) {
          return true;
        }
      }
      return false;
    }

  }

  class Solution3 {

    public List<List<Integer>> permute(int[] nums) {
      List<List<Integer>> result = new ArrayList<>();
      doWork(nums, 0, new Integer[nums.length], result);
      return result;
    }

    private void doWork(int[] nums, int pos, Integer[] store,
        List<List<Integer>> result) {
      if (pos == nums.length) {
        result.add(new ArrayList<>(Arrays.asList(store)));
        return;
      }
      for (int num : nums) {
        if (contains(store, pos, num)) {
          continue;
        }
        store[pos] = num;
        doWork(nums, pos + 1, store, result);
      }

    }

    private boolean contains(Integer[] arr, int endIndex, int target) {
      for (int j = 0; j < endIndex; j++) {
        if (arr[j] != null && arr[j] == target) {
          return true;
        }
      }
      return false;
    }

  }

  class Solution2 {

    public void permute(int[] nums) {
      doWork(nums, 0, "");
    }

    private void doWork(int[] nums, int pos, String prefix) {
      if (pos == nums.length) {
        System.out.println(prefix);
        return;
      }
      for (int num : nums) {
        if (prefix.contains(String.valueOf(num))) {
          continue;
        }
        doWork(nums, pos + 1, prefix + num);
      }

    }

  }

}
