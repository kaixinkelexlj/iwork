package com.fun.netty;

import com.alibaba.fastjson.JSON;

/**
 * @author xulujun
 * @date 2018/06/22
 */
public class TestClient {

    public static void main(String[] args) throws InterruptedException, RemotingTimeoutException, RemotingSendRequestException, RemotingConnectException {
        NettyRemotingClient client = new NettyRemotingClient(new NettyClientConfig());
        client.start();
        RemotingCommand request = new RemotingCommand();
        request.setValue("request from TestClient");
        RemotingCommand response = client.invokeSync("127.0.0.1:8888", request, 3000L);
        System.out.println(JSON.toJSONString(response));
        request.setValue("request from TestClient-new\n");
        response = client.invokeSync("127.0.0.1:8888", request, 3000L);
        System.out.println(JSON.toJSONString(response));
    }

}
