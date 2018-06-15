package com.work.job.sort;

public class BubbleSort<E extends Comparable<E>> {
	public final void sort(E[] list,int from, int to){
		for(int i = to;i > from; i--){
			for(int j = from;j < i;j++){
				if(list[j].compareTo(list[j + 1]) <= 0){
					E tmp = list[j + 1];
					list[j + 1] = list[j];
					list[j] = tmp;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		BubbleSort<Integer> s = new BubbleSort<Integer>();
		Integer[] list = new Integer[] {1, 3, 4, 5, 6, 2, 10};
		s.sort(list, 0, 6);
		for(Integer num : list){
			System.out.println(num);
		}
	}
}
