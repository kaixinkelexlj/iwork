package com.work.job.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

/**
 * @author xulujun
 * @date 2018/01/12
 */
public class PoolTest {

    /*int corePoolSize,
    int maximumPoolSize,
    long keepAliveTime,
    TimeUnit unit,
    BlockingQueue<Runnable> workQueue,
    RejectedExecutionHandler handler*/

    private MonitorableThreadPoolExecutor pool = new MonitorableThreadPoolExecutor("test", 2, 50, 30, TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(1), new CallerRunsPolicy());

    @Test
    public void test() throws Exception {

        Semaphore semaphore = new Semaphore(2);

        int size = 30;
        List<Callable<Object>> taskList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Integer val = i;
            taskList.add(() -> {
                boolean hasPermit = false;
                long start = System.currentTimeMillis();
                try {
                    System.out.println(Thread.currentThread().getName() + "::" + System.currentTimeMillis() + "==>tryAcquire");
                    hasPermit = semaphore.tryAcquire(3, TimeUnit.SECONDS);
                    if (hasPermit) {
                        Thread.sleep(50 + RandomUtils.nextInt(50));
                        System.out.println(Thread.currentThread().getName() + "==>" + val);
                        System.out.println(Thread.currentThread().getName() + "::" + (System.currentTimeMillis() - start) + "==>done");
                    } else {
                        System.out.println(Thread.currentThread().getName() + "::" + (System.currentTimeMillis() - start) + "==>no permit");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    semaphore.release();
                }
                return null;
            });
        }
        List<Future<Object>> list = pool.invokeAll(taskList);
        list.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

    }

}
