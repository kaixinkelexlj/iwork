package com.work.job.sort;

public abstract class Sorter<E extends Comparable<E>> {
	public abstract void sort(E[] array, int from, int len);

	public final void sort(E[] array) {
		sort(array, 0, array.length);
	}	
	protected final void swap(E[] array, int from, int to) {
		E tmp = array[from];
		array[from] = array[to];
		array[to] = tmp;
	}
	protected final void display(E[] array){
		for(E element:array){
			System.out.println(element);
		}
	}
	protected final void display(E[] array,int from,int len){
		for(int i = from;i<from+len;i++){
			System.out.println(array[i]);
		}
	}
	
	
}