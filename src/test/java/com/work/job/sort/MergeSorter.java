/**
 *  归并排序
 *  算法思想是每次把待排序列分成两部分，分别对这两部分递归地用归并排序，完成后把这两个子部分合并成一个
 *         序列。归并排序借助一个全局性临时数组来方便对子序列的归并，该算法核心在于归并。
 */
package com.work.job.sort;

import java.lang.reflect.Array;

/**
 *
 * 
 * @author liujin 
 * @param <E>
 */
public class MergeSorter<E extends Comparable<E>> extends Sorter<E> {

	@Override
	public void sort(E[] array, int from, int len) {
		// TODO Auto-generated method stub
		if (len <= 1)
			return;
		E[] temporary = (E[]) Array.newInstance(array[0].getClass(), len);
		merge_sort(array, from, from + len - 1, temporary);
	}
	/**
	 * 
	 * @param array
	 * @param from
	 * @param to
	 * @param temporary
	 */
	private final void merge_sort(E[] array, int from, int to, E[] temporary) {
		if (to <= from) {
			return;
		}
		int middle = (from + to) / 2;
		merge_sort(array, from, middle, temporary);
		merge_sort(array, middle + 1, to, temporary);
		
		
		merge(array, from, to, middle, temporary);
		/**
		int k = 0, leftIndex = 0, rightIndex = to - from;
		System.arraycopy(array, from, temporary, 0, middle - from + 1);
		for (int i = 0; i < to - middle; i++) {
			temporary[to - from - i] = array[middle + i + 1];
		}
		
		for(E i:temporary){
			if(i!=null){
			   System.out.print(i.toString()+" ");
			}
		}
		System.out.println("----temporary end-----");
		
		while (k < to - from + 1) {
			if (temporary[leftIndex].compareTo(temporary[rightIndex]) < 0) {
				array[k + from] = temporary[leftIndex++];
				System.out.println("array["+(int)(k + from)+"]="+array[k + from]);

			} else {
				array[k + from] = temporary[rightIndex--];
				System.out.println("array["+(int)(k + from)+"]="+array[k + from]);
			}
			k++;
		}
		
		for(E i:array){
			if(i!=null){
			System.out.print(i.toString()+" ");
			}
		}
        System.out.println("----array end-----");
        */
	}

	/**
	 * 
	 * @param array
	 * @param from
	 * @param to
	 * @param middle
	 * @param temporary
	 */
	private final void merge(E[] array, int from, int to, int middle,
			E[] temporary) {
		System.out.println(from+"   "+to+"   "+middle);
		int k = 0, leftIndex = 0, rightIndex = to - from;
		System.arraycopy(array, from, temporary, 0, middle - from + 1);
		for (int i = 0; i < to - middle; i++) {
			temporary[to - from - i] = array[middle + i + 1];
		}
		while (k < to - from + 1) {
			if (temporary[leftIndex].compareTo(temporary[rightIndex]) < 0) {
				array[k + from] = temporary[leftIndex++];

			} else {
				array[k + from] = temporary[rightIndex--];
			}
			k++;
		}

	}
}
