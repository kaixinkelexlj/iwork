package com.fun.netty;

/**
 * @author xulujun
 * @date 2018/06/14
 */
public class ServerTest {

    public static void main(String[] args) throws InterruptedException {
        NettyServerConfig config = new NettyServerConfig();
        NettyRemotingServer nettyRemotingServer = new NettyRemotingServer(config);
        nettyRemotingServer.start();
        Thread.currentThread().join();
    }


}
