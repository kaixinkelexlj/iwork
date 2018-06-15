package com.fun.codec;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Enumeration;

public class TestRSA {

    public static void main(String[] args) throws Exception{
        /*nputStream publicIn = TestRSA.class.getResourceAsStream("certs/public-rsa-10years.cer");
        InputStream privateIn = TestRSA.class.getResourceAsStream("certs/private-rsa.pfx");
        SecurityService.getInstance().init(publicIn, privateIn);
        
        String result = SecurityService.getInstance().encryptStringWithRSA("5ihhq");
        System.out.println("encryptStringWithRSA:" + result);
        
        System.out.println("decryptStringWithRSA:" 
                + SecurityService.getInstance().decryptStringWithRSA(result));*/

    }
    
    public void test() throws Exception{
        InputStream privateIn = TestRSA.class.getResourceAsStream("certs/private-rsa.pfx");
       
        KeyStore ks = KeyStore.getInstance("PKCS12");
        // 读取密钥仓库
        BufferedInputStream ksbufin = new BufferedInputStream(privateIn);
        char[] keyPassword = "alibaba".toCharArray();
        ks.load(ksbufin, keyPassword);
        Enumeration<String> aliasList =  ks.aliases();
        for(;aliasList.hasMoreElements();){
            System.out.println("alias:" + aliasList.nextElement());
        } 
    }

}
