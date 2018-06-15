/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lujun.xlj
 * @date 2017/7/7
 */
public class ThreadPoolTest {

    @Test
    public void testRun() throws Exception {
        final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
        //int threadSize = 21;
        int threadSize = 20;
        CountDownLatch start = new CountDownLatch(threadSize);
        CountDownLatch end = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        start.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("task start");
                    asyncTaskRunner.runTask(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                System.out.println(Thread.currentThread().getName());
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    end.countDown();
                }
            });
            thread.start();
            start.countDown();
        }

        end.await();

        System.out.println("task end");

    }

}

class AsyncTaskRunner {

    private ThreadPoolExecutor asyncThreadPool = new ThreadPoolExecutor(10, 10, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new NamedThreadFactory("asyncWorker"));

    public boolean runTask(Runnable task) {
        boolean result = false;
        try {
            asyncThreadPool.submit(task);
            result = true;
        } catch (RejectedExecutionException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}

class NamedThreadFactory implements ThreadFactory {

    static final AtomicInteger poolNumber   = new AtomicInteger(1);

    final AtomicInteger        threadNumber = new AtomicInteger(1);
    final ThreadGroup          group;
    final String               namePrefix;
    final boolean              isDaemon;

    public NamedThreadFactory(){
        this("pool");
    }

    public NamedThreadFactory(String name){
        this(name, false);
    }

    public NamedThreadFactory(String preffix, boolean daemon){
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = preffix + "-" + poolNumber.getAndIncrement() + "-thread-";
        isDaemon = daemon;
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        t.setDaemon(isDaemon);
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

}
