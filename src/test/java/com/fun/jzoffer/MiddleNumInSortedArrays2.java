package com.fun.jzoffer;

/**
 * @author xulujun 2020/05/28.
 *
 */
public class MiddleNumInSortedArrays2 {

  public static void main(String[] args) {
    double val1 = new Solution().findMedianSortedArrays(new int[]{1, 3}, new int[]{2});
    System.out.println(val1);
    double val2 = new Solution().findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4});
    System.out.println(val2);
  }

  public static class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
      int total = nums1.length + nums2.length;

      if (total % 2 == 1) {
        return findKthElement(nums1, nums2, total / 2 + 1);
      }

      return (findKthElement(nums1, nums2, total / 2) + findKthElement(nums1, nums2, total / 2 + 1))
          / 2.0d;

    }

    /**
     * 两个有序数组找到第k小的元素
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    private int findKthElement(int[] nums1, int[] nums2, int k) {
      int index1, index2; // 索引
      int offset1 = 0, offset2 = 0; // 步长
      while (true) {
        if (offset1 == nums1.length) {
          return nums2[offset2 + k - 1];
        }
        if (offset2 == nums2.length) {
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
