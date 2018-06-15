/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.proxy;

import java.util.List;

/**
 * 类ClassUtils.java的实现描述：ClassUtils.java
 * @author lujun.xlj Jan 17, 2017 4:23:15 PM
 */
public class ClassUtils {

    public static Class<?>[] getAllInterfaceArray(Class<?> type) {
        List<?> list = org.apache.commons.lang.ClassUtils.getAllInterfaces(type);
        Class<?>[] interfaces = list.toArray(new Class<?>[0]);
        return interfaces;
    }

    @SuppressWarnings("unchecked")
    public static List<Class<?>> getAllInterfaceList(Class<?> type) {
        return org.apache.commons.lang.ClassUtils.getAllInterfaces(type);
    }

}
