package com.work.job;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

/**
 * @author xulujun
 * @date 2017/12/14
 */
public class ThreadPoolMonitorTest {

    ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(20);
    ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

    public ThreadPoolMonitorTest() {
        timer.scheduleAtFixedRate(() -> {
            System.out.println(executorService);
            //executorService.getActiveCount()
            System.out.println(executorService.getQueue().size());
        }, 0, 500, TimeUnit.MICROSECONDS);
    }

    @Test
    public void runTask() throws Exception{
        int size = 500;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            executorService.submit(() -> {
                try {
                    java.lang.Thread.sleep(200 + RandomUtils.nextInt(500));
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();
    }

}
