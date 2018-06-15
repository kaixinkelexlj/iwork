/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.proxy;

/**
 * 类DefaultFun.java的实现描述：DefaultFun.java
 * @author lujun.xlj Jan 17, 2017 4:34:53 PM
 */
public class DefaultFun implements HelloFun {

    /* (non-Javadoc)
     * @see com.fun.proxy.HelloFun#sayFun()
     */
    @Override
    public String sayFun() {
        return "hello fun from default";
    }

}
