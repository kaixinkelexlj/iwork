/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job;

import com.work.CommonUtils;
import org.junit.Test;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lujun.xlj
 * @date 2017/7/27
 */
public class DateTImeTest {

    @Test
    public void testDate() throws Exception{
        Date now = new Date();
        System.out.println(CommonUtils.date2Str(CommonUtils.calDate(now, Calendar.DATE, -100)));
        System.out.println(CommonUtils.date2Str(Date.from(now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(-100).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }

}
