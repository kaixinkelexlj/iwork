/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.proxy;

/**
 * 类ProxyTest.java的实现描述：ProxyTest.java
 * @author lujun.xlj Jan 17, 2017 3:56:14 PM
 */
public class ProxyTest {

    public static void main(String[] args) {
        HelloFun obj = new DefaultFun();
        p("default", obj);

        HelloFun proxyJdk = (HelloFun) new JdkProxy().createProxy(obj);
        p("jdk", proxyJdk);

        HelloFun proxyCglib = (HelloFun) new CglibProxy().createProxy(obj);
        p("cglib", proxyCglib);

        /*HelloFun mix1 = (HelloFun) new JdkProxy().createProxy(proxyJdk);
        p("jdk+jdk", mix1);
        
        HelloFun mix4 = (HelloFun) new JdkProxy().createProxy(proxyCglib);
        p("jdk+cglib", mix4);
        
        try {
            HelloFun mix2 = (HelloFun) new CglibProxy().createProxy(proxyJdk);
            p("cglib+jdk", mix2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        /*HelloFun mix3 = (HelloFun) new CglibProxy().createProxy(proxyCglib);
        p("cglib+cglib", mix3);*/

    }

    public static void p(String prefix, HelloFun obj) {
        System.out.println("[" + prefix + "]" + obj.sayFun());
    }

}
