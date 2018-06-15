/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 * 类CglibProxy.java的实现描述：CglibProxy.java
 * @author lujun.xlj Jan 17, 2017 4:00:03 PM
 */
public class CglibProxy implements FunProxy {

    /* (non-Javadoc)
     * @see com.fun.proxy.FunProxy#createProxy(java.lang.Class)
     */
    @Override
    public Object createProxy(Object bean) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(ClassUtils.getAllInterfaceArray(bean.getClass()));
        enhancer.setCallbacks(new Callback[] { NoOp.INSTANCE, new FunMethodInterceptor(), new FunMethodInterceptor2() });
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                if ("sayFun".equals(method.getName())) {
                    //System.out.print(method.getDeclaringClass().getName() + "===>" + method.getName() + ";;");
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        //enhancer.setCallbacks(new Callback[] { new FunMethodInterceptor() });
        if (Proxy.isProxyClass(bean.getClass())) {
            enhancer.setSuperclass(null);
        } else {
            enhancer.setSuperclass(bean.getClass());//如果setSuperClass,cglib+cglib时执行superClass的sayFun方法
        }
        enhancer.setUseFactory(false);
        return enhancer.create();

    }

    private class FunMethodInterceptor implements MethodInterceptor {

        /* (non-Javadoc)
         * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
         */
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("invoke[" + method.getName() + "] from CglibProxy1");
            //return proxy.invoke(obj, args);//stackoverflow
            //return method.invoke(obj, args);//stackoverflow
            return proxy.invokeSuper(obj, args);
        }

    }

    private class FunMethodInterceptor2 implements MethodInterceptor {

        /* (non-Javadoc)
         * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
         */
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            return "invoke[" + method.getName() + "]from CglibProxy2";
        }

    }

}
