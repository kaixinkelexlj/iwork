package com.fun.codec;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class TestRSA2 {

    public static void main(String[] args) throws Exception {
        /*nputStream publicIn = TestRSA.class.getResourceAsStream("certs/public-rsa-10years.cer");
        InputStream privateIn = TestRSA.class.getResourceAsStream("certs/private-rsa.pfx");
        SecurityService.getInstance().init(publicIn, privateIn);
        
        String result = SecurityService.getInstance().encryptStringWithRSA("5ihhq");
        System.out.println("encryptStringWithRSA:" + result);
        
        System.out.println("decryptStringWithRSA:" 
                + SecurityService.getInstance().decryptStringWithRSA(result));*/

        testAes();
        // testAes2();
        // testAes3();
    }

    public void test() throws Exception {
        InputStream privateIn = TestRSA.class.getResourceAsStream("certs/private-rsa.pfx");

        KeyStore ks = KeyStore.getInstance("PKCS12");
        // 读取密钥仓库
        BufferedInputStream ksbufin = new BufferedInputStream(privateIn);
        char[] keyPassword = "alibaba".toCharArray();
        ks.load(ksbufin, keyPassword);
        Enumeration<String> aliasList = ks.aliases();
        for (; aliasList.hasMoreElements(); ) {
            System.out.println("alias:" + aliasList.nextElement());
        }
    }

    public static void testAes() throws Exception {

        String password = "wuli is niubility";
        // String content =
        // "{\"qq\":{\"appId\":\"1105121808\",\"secretKey\":\"kVXEk4uSU7XcBLKS\",\"version\":1},\"weibo\":{\"appId\":\"2924717636\",\"secretKey\":\"73370c054524b1a75e0872efeb7665a8\",\"version\":1},
        // \"weixin\":{\"appId\":\"wxf83d4a3e472914dc\",\"secretKey\":\"f6e34b22f5861f3318aaa34b64ae287c\",\"version\":1}}";

        String content = "1111111111111111111111";

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(password.getBytes()));
        SecretKey secretKey = kgen.generateKey();

        byte[] enCodeFormat = secretKey.getEncoded();
        System.out.println(CipherUtils.bytesToHexString(enCodeFormat));// 将来会给你这个作为密钥字符串
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec("0100100100100110".getBytes("utf-8")));// 初始化
        byte[] result = cipher.doFinal(byteContent);
        String resultString = CipherUtils.bytesToHexString(result);
        System.out.println(Base64.encodeBase64String(result));
        System.out.println(resultString);

        // 7CBCB254B981E20A7362BD3E1EB11C72

        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec("0100100100100110".getBytes("utf-8")));// 初始化
        result = cipher.doFinal(CipherUtils.hexStringToByte(resultString));
        // System.out.println(CipherUtils.bytesToHexString(result));
        System.out.println(new String(result, "utf-8")); // 解密
        byte[] iv = new IvParameterSpec("0100100100100110".getBytes("utf-8")).getIV();
        System.out.println(CipherUtils.bytesToHexString(iv));

    }

    public static void testAes2() throws Exception {
        SecretKeySpec key = new SecretKeySpec(CipherUtils.hexStringToByte("000102030405060708090a0b0c0d0e0f"), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
        byte[] byteContent = "12345".getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        System.out.println(result.length);
        String resultString = CipherUtils.bytesToHexString(result);
        System.out.println(Base64.encodeBase64String(result));
        System.out.println(resultString);

    }

    public static void testAes3() throws Exception {

        String content = "wuli is niuxwuli is niuxwuli is niuxwuli is niux";

        SecretKeySpec key = new SecretKeySpec(CipherUtils.hexStringToByte("0ec80a0992200ee35831e21fdfa812ca"), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
        byte[] byteContent = content.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        String resultString = CipherUtils.bytesToHexString(result);
        System.out.println(Base64.encodeBase64String(result));
        System.out.println(resultString);

        // 7CBCB254B981E20A7362BD3E1EB11C72

        /*cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        result = cipher.doFinal(CipherUtils.hexStringToByte(resultString));
        System.out.println(new String(result, "utf-8")); // 加密
        */
    }

}
