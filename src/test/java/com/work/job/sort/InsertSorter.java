package com.work.job.sort;

/**
 * 插入排序
 * @author EGOVA 
 * 该算法每次插入第K+1到前K个有序数组中一个合适位置，K从0开始到N-1,从而完成排序
 */
public class InsertSorter<E extends Comparable<E>> {
	public void sort(E[] array, int from, int len) {
		E tmp = null;
		for (int i = from + 1; i < from + len; i++) {
			tmp = array[i];
			int j = i;
			for (; j > from; j--) {
				if (tmp.compareTo(array[j - 1]) < 0) {
					array[j] = array[j - 1];
				} else
					break;
			}
			array[j] = tmp;
		}
	}
}
