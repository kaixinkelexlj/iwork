/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job;

/**
 * @author lujun.xlj
 * @date 2017/7/10
 */
public class GCTest {

    //-Xmx64M -Xms16M -Xss5M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintFlagsFinal -Xloggc:D:/logs/gc.log
    public static void main(String[] args) throws Exception {

        System.out.println(MemUtils.summary()); //max[1755], total[119], free[114]
        testGc();
    }

    public static void testGc() throws Exception {
        MemObject object = null;
        for (int i = 0; i < 10; i++) {
            if (i < 8) {
                object = new MemObject(MemUtils.Unit.MB.getVal());
            } else {
                object = new MemObject(8 * MemUtils.Unit.MB.getVal());
            }
            System.out.println(MemUtils.usedM());
            Thread.sleep(2000L);
        }

        object = new MemObject(MemUtils.Unit.GB.getVal());//out of memory
        System.out.println(MemUtils.summary());
        System.out.println(MemUtils.usedM());
    }

    public static class MemObject {

        public MemObject(){
        }

        public MemObject(int size){
            this.obj = new byte[size];
        }

        private byte[] obj;

        public byte[] getObj() {
            return obj;
        }

        public void setObj(byte[] obj) {
            this.obj = obj;
        }
    }

}
