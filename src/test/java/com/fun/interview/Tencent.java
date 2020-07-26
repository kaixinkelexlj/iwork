package com.fun.interview;

import com.fun.jzoffer.JzOffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author xulujun 2020/7/23
 *
 * 60块砖，60人搬，男搬5，女搬3，两个小孩搬1块，一次搬完，需要小孩、男人、女人各多少人？
 *
 */
public class Tencent extends JzOffer {

  @Test
  public void test() throws Exception {
    // new Solution().run();
    // new Solution().work1();
    // new Solution3().work(10);
    new Solution().work(60, new double[]{5.0d, 3.0d, 0.5d}, 0, 60, new int[3]);
  }

  static class Solution {


    void work(int workerTotal, double[] workerAbility, int workerIndex, double workLoad,
        int[] store) {
      if (workerTotal == 0 && workLoad == 0) {
        System.out.println(
            IntStream.of(store).mapToObj(String::valueOf).collect(Collectors.joining(",")));
        return;
      }
      if (workerIndex == workerAbility.length) {
        return;
      }
      double ability = workerAbility[workerIndex];
      // 0.5
      for (int c = 0; c * ability <= workLoad; c++) {
        store[workerIndex] = c;
        work(workerTotal - c, workerAbility, workerIndex + 1, workLoad - c * ability, store);
        store[workerIndex] = 0;
      }
    }


    void work1() {

      int personTotal = 60;
      int manAbility = 5, womanAbility = 3;
      double twoChildAbility = 1;
      int workTotal = 60;

      for (int m = 0; m <= workTotal / manAbility; m++) {
        for (int w = 0; w <= personTotal - m; w++) {
          /*for (int c = 0; c <= personTotal - m - w; c++) {
            if (m * manAbility + w * womanAbility + c * childAbility == workTotal
                && (m + w + c) == personTotal) {
              System.out.println(String.format("man=%s, woman=%s, child=%s", m, w, c));
            }
          }*/
          int c = personTotal - m - w;
          if (c * twoChildAbility / 2 + m * manAbility + w * womanAbility == workTotal) {
            System.out.println(String.format("man=%s, woman=%s, child=%s", m, w, c));
          }


        }
      }

    }

  }

  static class Solution2 {

    void work(int n) {
      List<Long> resultSet = new ArrayList<>();
      while (resultSet.size() < n) {
        long val = Double.valueOf(Math.floor(Math.random() * n)).longValue();
        if (!resultSet.contains(val)) {
          resultSet.add(val);
        }
      }
      System.out.println(resultSet.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }
  }

  static class Solution3 {

    void work(int n) {
      Random rnd = new Random();
      List<Integer> list = IntStream.range(0, n).boxed()
          .collect(Collectors.toList());
      for (int i = list.size(); i > 1; i--) {
        Collections.swap(list, i - 1, rnd.nextInt(i));
      }
      System.out.println(list.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

  }


}
