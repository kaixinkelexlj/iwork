package com.work.job.sort;

/** 
 *归并排序，要求待排序的数组必须实现Comparable接口 
 */
public class MergeSort2 {
	/** 
	 *利用归并排序算法对数组obj进行排序 
	 */
	public void sort(Comparable[] obj) {
		Comparable[] bridge;
		if (obj == null){
			throw new NullPointerException("The param can not be null!");
		}
		bridge = new Comparable[obj.length]; //初始化中间数组 
		mergeSort(obj, 0, obj.length - 1,bridge); //归并排序 
	}

	/** 
	 *将下标从left到right的数组进行归并排序 
	 *@param obj　要排序的数组的句柄 
	 *@param left 要排序的数组的第一个元素下标 
	 *@param right 要排序的数组的最后一个元素的下标 
	 */
	private void mergeSort(Comparable[] obj, int left, int right,Comparable[] bridge){
		if (left < right){
			int center = (left + right) / 2;
			mergeSort(obj, left, center,bridge);
			mergeSort(obj, center + 1, right,bridge);
			merge(obj, left, center, right,bridge);
		}
	}

	/** 
	 *将两个对象数组进行归并，并使归并后为升序。归并前两个数组分别有序 
	 *@param obj 对象数组的句柄 
	 *@param left 左数组的第一个元素的下标 
	 *@param center 左数组的最后一个元素的下标 
	 *@param right 右数组的最后一个元素的下标 
	 */
	private void merge(Comparable[] obj, int left, int center, int right,Comparable[] bridge){
		int mid = center + 1;
		int third = left;
		int tmp = left;
		while (left <= center && mid <= right){
			//从两个数组中取出小的放入中间数组 
            if (obj[left].compareTo(obj[mid]) <= 0){
				bridge[third++] = obj[left++];
			}else 
				bridge[third++] = obj[mid++];
		}
		while (left <= center){
			bridge[third++] = obj[left++];
		}
		//剩余部分依次置入中间数组 
		while (mid <= right){
			bridge[third++] = obj[mid++];
		}
		//将中间数组的内容拷贝回原数组 
		//copy(obj, tmp, right,bridge);
		System.arraycopy(bridge, tmp, obj, tmp, right-tmp+1);
	}

	/** 
	 *将中间数组bridge中的内容拷贝到原数组中 
	 *@param obj 原数组的句柄 
	 *@param left 要拷贝的第一个元素的下标 
	 *@param right 要拷贝的最后一个元素的下标 
	 */
	private void copy(Comparable[] obj, int left, int right,Comparable[] bridge) {
		while (left <= right){
			obj[left] = bridge[left];
			left++;
		}
	}
	public static void main(String[] args) {
		Integer[] array = { 12, 4, 28, 104, 2, 3, 0, 123 };
		//Integer[] array = {12,4,28};
		MergeSort2 sort = new MergeSort2();
		sort.sort(array);
		for (Integer i : array) {
			System.out.print(i + " ");
		}
	}

}
