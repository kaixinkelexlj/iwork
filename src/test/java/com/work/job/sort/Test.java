package com.work.job.sort;

public class Test {
   public static void main(String[] args){
	   Integer[] array = {12,4,28,104,2,3,0,123};
	   //Integer[] array = {12,4,28};
	   Sorter<Integer> sort = new SelectSorter<Integer>();
	   sort.sort(array,0,array.length);
	   sort.display(array);
   }
}
//MergeSorter
//12 4 ----temporary end-----
//array[0]=4
//array[1]=12
//4 12 28 104 2 3 0 123 ----array end-----
//28 104 ----temporary end-----
//array[2]=28
//array[3]=104
//4 12 28 104 2 3 0 123 ----array end-----
//4 12 104 28 ----temporary end-----
//array[0]=4
//array[1]=12
//array[2]=28
//array[3]=104
//4 12 28 104 2 3 0 123 ----array end-----
//2 3 104 28 ----temporary end-----
//array[4]=2
//array[5]=3
//4 12 28 104 2 3 0 123 ----array end-----
//0 123 104 28 ----temporary end-----
//array[6]=0
//array[7]=123
//4 12 28 104 2 3 0 123 ----array end-----
//2 3 123 0 ----temporary end-----
//array[4]=0
//array[5]=2
//array[6]=3
//array[7]=123
//4 12 28 104 0 2 3 123 ----array end-----
//4 12 28 104 123 3 2 0 ----temporary end-----
//array[0]=0
//array[1]=2
//array[2]=3
//array[3]=4
//array[4]=12
//array[5]=28
//array[6]=104
//array[7]=123
//0 2 3 4 12 28 104 123 ----array end-----
//0
//2
//3
//4
//12
//28
//104
//123