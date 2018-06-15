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
public final class MemUtils {

    public static enum Unit {
        MB(1024 * 1024), GB(1024 * 1024 * 1024);

        private int val;

        private Unit(int val){
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public static String summary() {
        return String.format("max[%s], total[%s], free[%s]", max(Unit.MB), total(Unit.MB), free(Unit.MB));
    }

    public static long max() {
        return Runtime.getRuntime().maxMemory();
    }

    public static long total() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long free() {
        return Runtime.getRuntime().freeMemory();
    }

    public static long max(Unit unit) {
        return Runtime.getRuntime().maxMemory() / unit.val;
    }

    public static long total(Unit unit) {
        return Runtime.getRuntime().totalMemory() / unit.val;
    }

    public static long free(Unit unit) {
        return Runtime.getRuntime().freeMemory() / unit.val;
    }

    public static long usedM() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Unit.MB.val;
    }

    public static long userG() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Unit.GB.val;
    }

    private MemUtils(){
    }

}
