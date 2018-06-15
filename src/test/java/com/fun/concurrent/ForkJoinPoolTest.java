/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.concurrent;

import org.apache.commons.lang.math.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * 
 * @author lujun.xlj
 * @version $Id: ForkJoinPoolTest.java, v 0.1 Jan 5, 2016 2:47:23 PM lujun.xlj Exp $
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) throws Exception {

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);

        /*List<PrintTask> taskList = Arrays.asList(new PrintTask("hello"), new PrintTask("world"), new PrintTask("xingmo"));

        List<Future<Boolean>> resultList = pool.invokeAll(taskList);

        System.out.println(resultList.get(2).get());*/

        int size = 100;
        List<RandomTask> tasks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            tasks.add(new RandomTask(RandomUtils.nextInt(100)));
        }
        List<OpResult> results = pool.invoke(new BatchTask(tasks));
        results.forEach(System.out::println);

    }

    public static class PrintTask implements Callable<Boolean> {

        private String taskName;

        public PrintTask(String taskName){
            this.taskName = taskName;
        }

        /**
         * @see java.util.concurrent.Callable#call()
         */
        @Override
        public Boolean call() throws Exception {
            System.out.println(String.format("%s==>%s", this.taskName, Thread.currentThread().getName()));
            Thread.sleep(2000);
            return true;
        }

    }

    public static class BatchTask extends ForkJoinTask<List<OpResult>> {

        private static final long serialVersionUID = -8567422091328782159L;
        private List<RandomTask>  tasks;
        private List<OpResult>    resultList;

        public BatchTask(List<RandomTask> tasks){
            this.tasks = tasks;
            resultList = new ArrayList<>(tasks.size());
        }

        @Override
        public List<OpResult> getRawResult() {
            return resultList;
        }

        @Override
        protected void setRawResult(List<OpResult> value) {

        }

        @Override
        protected boolean exec() {
            invokeAll(tasks);
            tasks.forEach(task -> resultList.add(task.join()));
            return true;
        }
    }

    public static class RandomTask extends ForkJoinTask<OpResult> {

        private static final long serialVersionUID = 6424882443283115624L;
        private int               num;

        public RandomTask(int num){
            this.num = num;
        }

        @Override
        public OpResult getRawResult() {
            /*if(num % 2 != 0){
                throw new RuntimeException("error");
            }*/
            return new OpResult(num, num % 2 == 0);
        }

        @Override
        protected void setRawResult(OpResult value) {

        }

        @Override
        protected boolean exec() {
            System.out.println(Thread.currentThread().getName() + ",handle==>" + num);
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static class OpResult {

        private boolean success;
        private int     num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public OpResult(int num, boolean success){
            this.success = success;
            this.num = num;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        @Override
        public String toString() {
            return num + "==>" + success;
        }
    }

}
