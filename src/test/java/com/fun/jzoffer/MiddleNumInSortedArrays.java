package com.fun.jzoffer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author xulujun 2020/05/28.
 *
 * https://leetcode-cn.com/problems/median-of-two-sorted-arrays
 *
 * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 *
 * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。

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
public class MiddleNumInSortedArrays {

  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int total = nums1.length + nums2.length;
    if (total % 2 == 1) {
      return findKthElement(nums1, nums2, total / 2 + 1);
    }
    return (findKthElement(nums1, nums2, total / 2) + findKthElement(nums1, nums2, total / 2 + 1))
        / 2.0f;
  }

  /**
   * 两个有序数组找到第k小的元素
   * @param nums1
   * @param nums2
   * @param k
   * @return
   */
  private int findKthElement(int[] nums1, int[] nums2, int k) {

    /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
     * 这里的 "/" 表示整除
     * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
     * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
     * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
     * 这样 pivot 本身最大也只能是第 k-1 小的元素
     * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
     * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
     * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
     */

    int index1, index2;
    int offset1 = 0, offset2 = 0;

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

  @Test
  public void test() throws Exception {
    Assert.assertTrue(findMedianSortedArrays(new int[]{1, 3}, new int[]{2}) == 2.0);
    Assert.assertTrue(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}) == 2.5);
  }

}
