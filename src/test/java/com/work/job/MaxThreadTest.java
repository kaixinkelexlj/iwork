/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job;

import java.util.concurrent.CountDownLatch;

/**
 * @author lujun.xlj
 * @date 2017/7/10
 */
public class MaxThreadTest {

    public static void main(String[] args) {
        for (int i = 0;; i++) {
            System.out.println(i);
            new HoldThread().start();
        }
    }

}

class HoldThread extends Thread {

    CountDownLatch latch = new CountDownLatch(1);

    public HoldThread(){
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
