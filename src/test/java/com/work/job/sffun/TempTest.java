package com.work.job.sffun;

/**
 * @author xulujun
 * @date 2019/04/26
 */
public class TempTest {

  public static void main(String[] args) {
    printNum(null, 3);
  }

  public static void printNum(String prefix, int n){
    if(prefix == null){
      prefix = "";
    }
    for(int i = 0;i < 10;i++){
      if(prefix.length() >= n - 1){
        System.out.println(prefix + i);
      }else{
        printNum(prefix + i, n);
      }

    }
  }

}
