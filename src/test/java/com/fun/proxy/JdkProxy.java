/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 类JdkProxy.java的实现描述：JdkProxy.java
 * @author lujun.xlj Jan 17, 2017 3:59:55 PM
 */
public class JdkProxy implements FunProxy {

    /* (non-Javadoc)
     * @see com.fun.proxy.FunProxy#createProxy(java.lang.Class)
     */
    @Override
    public Object createProxy(final Object bean) {

        return Proxy.newProxyInstance(bean.getClass().getClassLoader(), com.fun.proxy.ClassUtils.getAllInterfaceArray(bean.getClass()), new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("invoke[" + method.getName() + "]from JdkProxy");
                //return method.invoke(proxy, args);//stackoverflow
                return method.invoke(bean, args);
            }
        });
    }


}
