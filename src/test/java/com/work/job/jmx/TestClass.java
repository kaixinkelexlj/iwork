package com.work.job.jmx;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;

import org.junit.Test;

/**
 * @author xulujun
 * @date 2017/11/03
 */
public class TestClass implements Serializable {

    private static final long serialVersionUID = -3119071188963772632L;

    public static void main(String[] args) {

        System.out.println("idea enviroment is ok now");
        List<Integer> list = Stream.of(1, 2, 3, 4).filter(i -> i % 2 == 0).collect(Collectors.toList());
        List<Integer> list2 = Stream.of(1, 2, 3, 4).filter(i -> i % 2 == 0).collect(Collectors.toList());
        List<Integer> list3 = Stream.of(1, 2, 3, 4).filter(i -> i % 2 == 0).collect(Collectors.toList());
        List<Integer> list4 = Stream.of(1, 2, 3, 4).filter(i -> i % 2 == 0).collect(Collectors.toList());
        List<Integer> list5 = Stream.of(1, 2, 3, 4).filter(i -> i % 2 == 0).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));

        List<String> a = new ArrayList<>(100);
        System.out.println(a.size());

        List<String> list100 = new ArrayList<>(100);
        System.out.println(JSON.toJSONString(list100));
    }

    @Test
    public void testGeneric() throws Exception {
        List<String> a = new ArrayList<>(100);
        System.out.println(JSON.toJSONString(
            ((ParameterizedType)a.getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
    }
}
