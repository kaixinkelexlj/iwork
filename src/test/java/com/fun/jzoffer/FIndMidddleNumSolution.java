package com.fun.jzoffer;

import org.junit.Test;

/**
 * @author xulujun 2020/07/18.
 *
 * 示例 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * 则中位数是 2.0
 * 示例 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * 则中位数是 (2 + 3)/2 = 2.5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class FIndMidddleNumSolution extends JzOffer {

  @Test
  public void test() throws Exception {
    double result = new Solution().findMedianSortedArrays(new int[]{1, 3}, new int[]{2});
    testAndPrintout(result == 2.0, result);
    result = new Solution().findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4});
    testAndPrintout(result == 2.5, result);
  }


  class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
      int len1 = nums1.length;
      int len2 = nums2.length;
      if ((len1 + len2) % 2 == 1) {
        // 单数 3: k = 2
        return findKthElement(nums1, nums2, (len1 + len2) / 2 + 1);
      } else {
        // 双数 4: k = 2,3
        return (findKthElement(nums1, nums2, (len1 + len2) / 2) + findKthElement(nums1, nums2,
            (len1 + len2) / 2 + 1))
            / 2.0d;
      }
    }

    // k = 3
    private int findKthElement(int[] nums1, int[] nums2, int k) {
      int index1, index2;
      int offset1 = 0, offset2 = 0;
      for (; ; ) {
        if (offset1 >= nums1.length) {
          return nums2[offset2 + k - 1];
        }
        if (offset2 >= nums2.length) {
          return nums1[offset1 + k - 1];
        }
        if (k == 1) {
          return Math.min(nums1[offset1], nums2[offset2]);
        }
        index1 = Math.min(offset1 + k / 2, nums1.length) - 1;
        index2 = Math.min(offset2 + k / 2, nums2.length) - 1;
        if (nums1[index1] <= nums2[index2]) {
          k = k - (index1 - offset1) - 1;
          offset1 = index1 + 1;
        } else {
          k = k - (index2 - offset2) - 1;
          offset2 = index2 + 1;
        }
      }

    }
  }


}
