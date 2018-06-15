package com.work.job.sort;

public class SelectSorter<E extends Comparable<E>> extends Sorter<E> {

	@Override
	public void sort(E[] array, int from, int len) {
		for (int i = from; i < from + len; i++) {
			int smallest = i;
			for (int j = from + len - 1; j >=i; j--) {
				if (array[j].compareTo(array[smallest]) < 0) {
					smallest=j;
				}
			}
			swap(array, i, smallest);
		}
	}

}
