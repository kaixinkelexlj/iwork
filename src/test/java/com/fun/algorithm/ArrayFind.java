package com.fun.algorithm;

import org.junit.Test;

/**
 * @author xulujun
 * @date 2018/06/13
 *
 *
 * https://github.com/gatieme/CodingInterviews/tree/master/003-%E4%BA%8C%E7%BB%B4%E6%95%B0%E7%BB%84%E4%B8%AD%E7%9A%84%E6%9F%A5%E6%89%BE
 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class ArrayFind {

    /**
     * 右上角
     *
     * @param array
     * @param target
     * @return
     */
    private boolean findFromRightUp(int[][] array, int target) {
        int cols = array[0].length;
        int rows = array.length;
        System.out.println(String.format("rows:%s, cols:%s", rows, cols));
        int rowsIndex = 0;
        int colIndex = cols - 1;
        int e;

        for (; colIndex >= 0 && rowsIndex < rows; ) {
            e = array[rowsIndex][colIndex];
            if (e == target) {
                System.out.println(String.format("find target, rowsIndex[%s], colIndex[%s]", rowsIndex, colIndex));
                return true;
            } else if (target < e) {
                colIndex--;
            } else {
                rowsIndex++;
            }
        }

        return false;
    }

    /**
     * 左下角
     *
     * @param array
     * @param target
     * @return
     */
    private boolean findFromLeftBottom(int[][] array, int target) {
        int cols = array[0].length;
        int rows = array.length;
        System.out.println(String.format("rows:%s, cols:%s", rows, cols));
        int rowIndex = rows - 1;
        int colIndex = 0;
        int e;

        for (; rowIndex >= 0 && colIndex < cols;) {
            e = array[rowIndex][colIndex];
            if (e == target) {
                System.out.println(String.format("find target, rowsIndex[%s], colIndex[%s]", rowIndex, colIndex));
                return true;
            } else if (e > target) {
                rowIndex--;
            } else {
                colIndex++;
            }
        }
        return false;
    }

    @Test
    public void test() {

        int[][] array = {
            {1, 2, 8, 9},
            {2, 4, 9, 12},
            {4, 7, 10, 13},
            {6, 8, 11, 15}
        };
        System.out.println(findFromRightUp(array, 10));
        System.out.println(findFromLeftBottom(array, 7));
        System.out.println("done");

    }

}
