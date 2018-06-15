/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job.jmx;

import com.work.job.GCTest;
import com.work.job.MemUtils;
import org.junit.Test;

import java.lang.management.ManagementFactory;

/**
 * @author lujun.xlj
 * @date 2017/10/10
 */
public class JmxTest {

    @Test
    public void testManagerBean() throws Exception {

        System.out.println(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / MemUtils.Unit.MB.getVal());
        GCTest.MemObject memObject = new GCTest.MemObject(MemUtils.Unit.MB.getVal() * 100);
        System.out.println(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / MemUtils.Unit.MB.getVal());

    }

}
