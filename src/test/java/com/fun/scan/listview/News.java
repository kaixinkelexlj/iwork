/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.fun.scan.listview;

import com.fun.scan.annotation.ListView;


/**
 * 
 * @author lujun.xlj
 * @version $Id: News.java, v 0.1 Nov 17, 2015 5:15:31 PM lujun.xlj Exp $
 */
@ListView(url = "/news/list")
public class News implements com.fun.scan.listview.ListView{

    /** 
     * @see com.fun.scan.listview.ListView#render()
     */
    @Override
    public void render() {
        System.out.println("news");
    }
    
}
