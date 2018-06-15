package com.fun.algorithm;

import java.util.LinkedList;
import java.util.ListIterator;

import org.junit.Test;

/**
 * @author xulujun
 * @date 2018/06/13
 */
public class LinkedListReverse {

    private void reverse(ListIterator<Integer> itr) {
        Integer val;
        val = itr.hasNext() ? itr.next() : null;
        if (val != null) {
            reverse(itr);
            System.out.println(val);
        }
    }

    @Test
    public void test() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        reverse(list.listIterator());
    }

}
