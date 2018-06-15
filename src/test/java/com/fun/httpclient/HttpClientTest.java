/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author lujun.xlj
 * @version $Id: HttpClientTest.java, v 0.1 Apr 28, 2016 10:49:22 AM lujun.xlj Exp $
 */
public class HttpClientTest {

    @Test
    public void testGetXiaoer() throws Exception {
        Content content = Request.Get("https://ylb.m.taobao.com/yuwan/search.htm?keyword=%E5%B0%8F%E9%BB%84%E4%BA%BA/%E7%A5%9E%E5%81%B7%E5%A5%B6%E7%88%B8/%E5%8D%91%E9%84%99%E7%9A%84%E6%88%91&ywchannel=tbsearch").execute().returnContent();
        System.out.println(content.toString());
    }

    @Test
    public void getHttpClient() throws Exception {

        List<Header> headers = Arrays.asList(new BasicHeader(HttpHeaders.USER_AGENT, "crawler"));

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultHeaders(headers).setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(1000).setSocketTimeout(3000).build())
        //.setConnectionManager(new PoolingHttpClientConnectionManager())
        .build();
        CloseableHttpResponse response = httpClient.execute(new HttpGet("https://wwww.baidu.com"));
        System.out.println(EntityUtils.toString(response.getEntity()));

        httpClient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().build()).build();
        HttpPost post = new HttpPost("https://www.baidu.com");
        //post.setHeader();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("s", "httpclient"));
        post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
        response = httpClient.execute(post);
        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));

        response.close();
        httpClient.close();

    }

    @Test
    public void whenSettingCookiesOnTheHttpClient_thenCookieSentCorrectly() throws ClientProtocolException, IOException {
        BasicCookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "1234");
        cookie.setDomain(".github.com");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

        final HttpGet request = new HttpGet("http://www.github.com");
    }

}
