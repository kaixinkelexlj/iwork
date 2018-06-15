/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.fun.scan.listview;

import com.fun.scan.annotation.ListView;


/**
 * 
 * @author lujun.xlj
 * @version $Id: Star.java, v 0.1 Nov 17, 2015 5:15:22 PM lujun.xlj Exp $
 */
@ListView(url = "/star/list")
public class Star implements com.fun.scan.listview.ListView{

    /** 
     * @see com.fun.scan.listview.ListView#render()
     */
    @Override
    public void render() {
        System.out.println("star");
    }

}
