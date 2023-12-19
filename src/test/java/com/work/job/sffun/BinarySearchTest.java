package com.work.job.sffun;

import com.work.AbstractTest;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

/**
 * @author xulujun
 * @date 2019/04/30
 */
public class BinarySearchTest extends AbstractTest {

  public void test() throws Exception {

  }

  public <T> int BinarySearch(List<? extends Comparable<? super T>> list, T target ) {
    if(CollectionUtils.isEmpty(list)){
      return -1;
    }
    int low = 0, high = list.size() - 1;

    while(low <= high){
      int mid = (low + high) >>> 1;
      if(Objects.equals(list.get(mid), target)){
        return mid;
      }else{
        int val = list.get(mid).compareTo(target);
        if(val > 0){
          high = mid - 1;
        }else {
          low = mid + 1;
        }
      }
    }
    return -1;
  }

  @Test
  public void testFunc() throws Exception{
    Integer a = null;
    Integer b = 1;
    Integer c = null;
    System.out.println(b.equals(c));
    System.out.println(a == null);
  }

}
