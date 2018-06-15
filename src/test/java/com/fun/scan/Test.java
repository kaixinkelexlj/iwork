/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.fun.scan;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fun.scan.listview.News;


/**
 * 
 * @author lujun.xlj
 * @version $Id: Test.java, v 0.1 Nov 17, 2015 5:36:19 PM lujun.xlj Exp $
 */
public class Test {

    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("test-scan.xml");
        News news = context.getBean(News.class);
        news.render();

        ListViewRegister reg = context.getBean(ListViewRegister.class);
        reg.getUriMap().get("/news/list").render();
        reg.getNameMap().get("news").render();

    }
}
