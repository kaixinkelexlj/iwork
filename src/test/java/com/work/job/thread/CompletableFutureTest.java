/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job.thread;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

/**
 * @author lujun.xlj
 * @date 2017/9/28
 */
public class CompletableFutureTest {

    private static volatile AtomicInteger seq = new AtomicInteger(0);

    @Test
    public void test1() throws Exception {
        IntStream.range(1, 100).forEach(i -> {
            CompletableFuture.supplyAsync(() -> downloadImage(i))
                .thenAccept(CompletableFutureTest::renderPage);
        });

        Thread.sleep(20000);

    }

    @Test
    public void test2() throws Exception {
        List<CompletableFuture<Void>> list = IntStream.range(1, 100).mapToObj(i -> {
            return CompletableFuture.supplyAsync(() -> downloadImage(i))
                .thenAccept(CompletableFutureTest::renderPage);
        }).collect(Collectors.toList());

        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
        System.out.println("#########all done!!");

    }

    @Test
    public void test3() throws Exception {
        List<CompletableFuture<Integer>> list = IntStream.range(1, 100).mapToObj(i -> {
            return CompletableFuture.supplyAsync(() -> downloadImage(i))
                .thenApplyAsync(CompletableFutureTest::renderPage);
        }).collect(Collectors.toList());

        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
        System.out.println("#########all done!!");

    }

    private static ImageData downloadImage(int imageId) {
        try {
            Thread.sleep(RandomUtils.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("download image over, imageId[" + imageId + "]");
        return new ImageData(imageId, "https://image/" + imageId);
    }

    private static Integer renderPage(ImageData imageData) {
        try {
            Thread.sleep(RandomUtils.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("render page, imageData[" + imageData.getUrl() + "]");
        return imageData.getId();
    }

    private static class ImageData {

        public ImageData() {
        }

        public ImageData(Integer id, String url) {
            this.url = url;
            this.id = id;
        }

        private String url;
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
