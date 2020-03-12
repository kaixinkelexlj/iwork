package com.fun.jzoffer;

import java.util.LinkedList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun 2020/03/11.
 *
 * 给定数组，数组元素代表币值, 都是正整数；给定target，使用任意张，求换法总数
 *
 * e.g.
 *
 * arr = [5, 10, 25, 1], target = 0 返回1, 全部都不用
 * arr = [5, 10, 25, 1], target = 15 返回6, 6中换法
 * arr = [3, 5], target = 2, 返回0, 任意组合都不行
 *
 *
 */
public class MoneyChange {


  public int changeMoney(int[] arr, int target) {
    if (arr == null || arr.length == 0) {
      return 0;
    }
    if (target < 0) {
      return 0;
    }
    if (target == 0) {
      return 1;
    }
    return changeInternal(arr, 0, target, new LinkedList<>());
  }

  private int changeInternal(int[] arr, int index, int target, LinkedList<String> store) {
    int hits = 0;
    /*if (index == arr.length) {
      hits = target == 0 ? 1 : 0;
      if (hits == 1 && CollectionUtils.isNotEmpty(store)) {
        System.out.println("hit:" + StringUtils.join(store, ";"));
      }
      return hits;
    }*/
    if(target == 0){
      System.out.println("hit:" + StringUtils.join(store, ";"));
      return 1;
    }
    if(index == arr.length){
      return 0;
    }
    int curMoneyVal = arr[index];
    for (int count = 0; curMoneyVal * count <= target; count++) {
      store.push(curMoneyVal + "," + count);
      hits += changeInternal(arr, index + 1, target - curMoneyVal * count, store);
      store.pop();
    }
    return hits;
  }


  @Test
  public void test() throws Exception {
    int[] arr = new int[]{5, 10, 25, 1};
    System.out.println(changeMoney(arr, 0));
    System.out.println(changeMoney(arr, 15));
    System.out.println(changeMoney(new int[]{2, 3}, 1));
  }

}
