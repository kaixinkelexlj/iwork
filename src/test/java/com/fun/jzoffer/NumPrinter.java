package com.fun.jzoffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.Test;

/**
 * @author xulujun 2020/11/18.
 */
public class NumPrinter {

  @Test
  public void test1() throws Exception {
    PrintWorker worker = new PrintWorker(100);
    new Thread(worker, "thread1").start();
    new Thread(worker, "thread2").start();
  }

  @Test
  public void test2() throws Exception {
    PrintWorkerBeauty worker = new PrintWorkerBeauty(100);
    new Thread(worker, "thread1").start();
    new Thread(worker, "thread2").start();
  }

  public static class PrintWorker implements Runnable {

    private final Object lock = new Object();
    private volatile int num;
    private int limit;

    public PrintWorker(int limit) {
      this.limit = limit;
    }

    @Override
    public void run() {
      synchronized (lock) {
        while (num < limit) {
          System.out.println(Thread.currentThread().getName() + ":" + (++num));
          try {
            lock.notifyAll();
            lock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }

  }

  public static class PrintWorkerBeauty implements Runnable {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private volatile int num;
    private int limit;

    public PrintWorkerBeauty(int limit) {
      this.limit = limit;
    }

    @Override
    public void run() {
      lock.lock();
      try {
        while (num < limit) {
          System.out.println(Thread.currentThread().getName() + ":" + (++num));
          try {
            condition.signalAll();
            condition.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

      } finally {
        lock.unlock();
      }
    }
  }

}
