/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job.serializertest;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author lujun.xlj
 * @date 2017/7/13
 */
public class SingletonObject extends SimpleObject implements Serializable {

    private static final long              serialVersionUID = 4741383600486897171L;
    private static final SingletonObject inst             = new SingletonObject();

    private SingletonObject(){

    }

    public static SingletonObject getInstant() {
        return inst;
    }

    private Object readSolve(){
        return inst;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
