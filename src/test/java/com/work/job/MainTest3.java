package com.work.job;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author xulujun 2019/08/30
 */
public class MainTest3 {

  ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

  public MainTest3() {

  }

  public void start() {
    timer.scheduleAtFixedRate(() -> {
      System.out.println("doing");
    }, 0, 1000, TimeUnit.MILLISECONDS);
    /*Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("timer exit");
      timer.shutdownNow();
    }));*/
  }

  public void close() {
    timer.shutdownNow();
  }

  public static void main(String[] args) throws Exception {
    MainTest3 obj = new MainTest3();
    obj.start();
    Thread.sleep(10000L);
    System.out.println("exit");
    obj.close();
    //System.exit(0);
  }

}
