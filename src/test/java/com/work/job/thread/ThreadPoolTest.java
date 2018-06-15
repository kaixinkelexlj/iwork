/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.lucene.util.NamedThreadFactory;
import org.junit.Test;

/**
 * @author lujun.xlj
 * @date 2017/7/27
 */
public class ThreadPoolTest {

    ExecutorService pool = Executors.newFixedThreadPool(10, new NamedThreadFactory("test-pool"));

    @Test
    public void testCompletionService() throws Exception {
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(pool);

        List<Long> idList = LongStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());
        AtomicInteger total = new AtomicInteger(0);
        idList.forEach(i -> cs.submit(() -> {
            if (i == 1) {
                Thread.sleep(5000);
                System.out.println("##########" + Thread.currentThread().getName() + "==>" + i);
            } else {
                Thread.sleep(RandomUtils.nextInt(10));
                System.out.println(Thread.currentThread().getName() + "==>" + i);
            }
            return i.intValue();
        }));

        for (int i = 0; i < idList.size(); i++) {
            System.out.println(cs.take().get());
        }

        System.out.println("end==>" + total.get());

    }

    @Test
    public void test() throws Exception {
        List<Long> idList = LongStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());
        List<Long> taskList = null;
        int pageSize = 100;
        int pageNo = 1;
        List<Future<Integer>> resultList;
        AtomicInteger total = new AtomicInteger(0);
        for (; ; ) {
            taskList = subList(idList, (pageNo - 1) * pageSize, (pageNo - 1) * pageSize + pageSize);
            if (taskList == null || taskList.size() == 0) {
                break;
            }
            resultList = consume(taskList);
            //resultList = consumeAll(taskList);
            resultList.forEach(f -> {
                try {
                    Integer i = f.get();
                    System.out.println("#########first return ==> " + i);
                    total.addAndGet(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            pageNo++;

        }
        System.out.println("end==>" + total.get());
    }

    private List<Future<Integer>> consume(List<Long> taskList) {
        if (org.springframework.util.CollectionUtils.isEmpty(taskList)) {
            return new ArrayList<>(0);
        }
        List<Future<Integer>> list = new ArrayList<>(taskList.size());
        taskList.forEach(i -> {
            list.add(pool.submit(() -> {
                if (i == 1) {
                    Thread.sleep(5000);
                    System.out.println("##########" + Thread.currentThread().getName() + "==>" + i);
                } else {
                    Thread.sleep(RandomUtils.nextInt(10));
                    System.out.println(Thread.currentThread().getName() + "==>" + i);
                }
                return i.intValue();
            }));
        });
        return list;
    }

    private List<Future<Integer>> consumeAll(List<Long> taskList) throws InterruptedException {
        if (org.springframework.util.CollectionUtils.isEmpty(taskList)) {
            return new ArrayList<>(0);
        }
        return pool.invokeAll(taskList.stream().map(i -> {
            return new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {
                    if (i == 1) {
                        Thread.sleep(5000);
                        System.out.println("##########" + Thread.currentThread().getName() + "==>" + i);
                    } else {
                        Thread.sleep(RandomUtils.nextInt(10));
                        System.out.println(Thread.currentThread().getName() + "==>" + i);
                    }
                    return i.intValue();
                }
            };
        }).collect(Collectors.toList()));
    }

    public static <T> List<T> subList(List<T> sourceList, int startIndex, int endIndex) {
        if (sourceList == null) {
            return null;
        }
        if (sourceList.size() == 0) {
            return new ArrayList<T>(0);
        }
        if (startIndex < 0) {
            return new ArrayList<T>(0);
        }
        if (startIndex > endIndex) {
            return new ArrayList<T>(0);
        }
        if (startIndex > sourceList.size()) {
            return new ArrayList<T>(0);
        }
        if (endIndex > sourceList.size()) {
            endIndex = sourceList.size();
        }
        return sourceList.subList(startIndex, endIndex);
    }

}
