package com.fun.netty;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xulujun
 * @date 2018/06/14
 */
public class RemotingCommand {

    private static AtomicInteger RequestId = new AtomicInteger(0);

    private int opaque = RequestId.incrementAndGet();

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOpaque() {
        return opaque;
    }

    public void setOpaque(int opaque) {
        this.opaque = opaque;
    }
}
