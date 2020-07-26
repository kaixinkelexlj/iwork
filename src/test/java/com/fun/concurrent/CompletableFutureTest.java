package com.fun.concurrent;

import com.fun.jzoffer.JzOffer;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

/**
 * @author xulujun 2020/07/22.
 */
public class CompletableFutureTest extends JzOffer {

  @SuppressWarnings("unchecked")
  @Test
  public void testcompletedFutureExample() throws Exception {
    CompletableFuture cf = CompletableFuture.completedFuture("ok");
    System.out.println(cf.isDone());
    System.out.println(cf.get(100, TimeUnit.MILLISECONDS));
    System.out.println(cf.getNow(null));
  }

  @Test
  public void testRunAsync() throws Exception {
    CompletableFuture cf = CompletableFuture.runAsync(() -> {
      sleepQuietly(Math.round(Math.random() * 1000));
    });
    System.out.println(cf.isDone());
    sleepQuietly(1000);
    System.out.println(cf.isDone());
  }

  @Test
  public void testThenApply() throws Exception {
    CompletableFuture<String> cf = CompletableFuture.completedFuture("ok")
        .thenApply(String::toUpperCase);
    cf.thenAccept(s -> System.out.println("s:" + s));
    System.out.println(cf.getNow(null));
  }

  @Test
  public void testHandleException() throws Exception {
    CompletableFuture<String> cf = CompletableFuture.completedFuture("ok")
        .thenApplyAsync(val -> {
          sleepQuietly(1000);
          return val.toUpperCase();
        });
    CompletableFuture exHandle = cf.handle((val, ex) -> {
      if (ex != null) {
        System.out.println("WARN!! ==> exception happen");
        ex.printStackTrace();
        return "exception";
      }
      return val;
    });
    cf.completeExceptionally(new RuntimeException("some error happen"));
    String val = cf.join();
    System.out.println(val);
    System.out.println(cf.isCompletedExceptionally());
    System.out.println(exHandle.join());
  }

  @Test
  public void testCancel() throws Exception {
    CompletableFuture<String> cf = CompletableFuture.completedFuture("ok")
        .thenApplyAsync(val -> {
          sleepQuietly(1000);
          return val.toUpperCase();
        });
    CompletableFuture exHandle = cf.handle((val, ex) -> {
      if (ex instanceof CancellationException) {
        System.out.println("WARN!! ==> cal cancel");
        return "cancel";
      } else if (ex != null) {
        System.out.println("WARN!! ==> exception happen");
        ex.printStackTrace();
        return "exception";
      }
      return val;
    });
    System.out.println("cancel:" + cf.cancel(true));
    System.out.println("completedExceptionally:" + cf.isCompletedExceptionally());
    System.out.println(exHandle.join());
  }

  @Test
  public void testApplyToEither() throws Exception {
    CompletableFuture<String> cf = CompletableFuture.completedFuture("ok")
        .thenApplyAsync(val -> {
          sleepQuietly(1000);
          return val.toUpperCase();
        });
    CompletableFuture<String> result = cf.applyToEither(CompletableFuture.completedFuture("other"),
        s -> "ok:" + s);
    System.out.println(result.join());
  }


  @Test
  public void testAcceptEither() throws Exception {
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
      sleepQuietly(RandomUtils.nextInt(3));
      return "cf1";
    });
    cf.acceptEither(CompletableFuture.supplyAsync(() -> {
      sleepQuietly(RandomUtils.nextInt(3));
      return "cf2";
    }), s -> {
      System.out.println(String.format("ok, get:%s", s));
    });
    System.out.println(cf.join());
  }

  @Test
  public void testRunAfterBoth() throws Exception {
    StringBuilder builder = new StringBuilder();
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
      sleepQuietly(RandomUtils.nextInt(3));
      builder.append("cf1").append(";");
      return "cf1";
    });
    cf.runAfterBoth(CompletableFuture.supplyAsync(() -> {
      sleepQuietly(RandomUtils.nextInt(3));
      builder.append("cf2").append(";");
      return "cf2";
    }), () -> System.out.println("all done, out:" + builder.toString()));
  }

  @Test
  public void testCombine() throws Exception {
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
      sleepQuietly(RandomUtils.nextInt(3));
      return "cf1";
    });
    cf.thenCombine(CompletableFuture.supplyAsync(() -> {
      sleepQuietly(RandomUtils.nextInt(3));
      return "cf2";
    }), (s1, s2) -> s1 + "," + s2);
    System.out.println(cf.join());
  }


}
