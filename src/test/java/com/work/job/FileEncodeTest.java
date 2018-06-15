package com.work.job;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public class FileEncodeTest {
    
    public static void main(String[] args) throws Exception{
        File f1Utf8 = new File("e:\\常用资料\\alibaba\\memo.txt");
        File f2GBK = new File("F:\\alibaba\\deylbwap\\20150105_163120_activity_1\\deylbwap\\deylbwap-wap\\src\\main\\webapp\\wap\\templates\\screen\\dream\\ls.vm");
        System.out.println(new FileEncodeTest(f1Utf8).getCharset());
        System.out.println(new FileEncodeTest(f2GBK).getCharset());
        //new FileEncodeTest(f1Utf8).test();
    }
    
    
    private File file;
    
    public FileEncodeTest(File f){
        this.file = f;
    }
    
    public void test() throws Exception{
        BufferedInputStream bin = null;
        try{
            bin = new BufferedInputStream(new FileInputStream(file));
            int a = (bin.read() << 8) + bin.read();
            System.out.println(Integer.toHexString(a));
        }finally{
            if(bin != null){
                bin.close();
            }
        }
    }
    
    public Charset getCharset(){
        if(!this.file.exists() || !this.file.isFile()){
            throw new RuntimeException("file not exists or not a file");
        }
        byte[] start = new byte[4];
        
        InputStream in = null;
        try{
            in = new FileInputStream(file);
            in.read(start);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(in != null){
                try{
                    in.close();
                }catch(Exception ex){}
            }
        }
        return new EncodeGetter(start).getCharset();
    }
    
    
    /**
     *  US-ASCII 7 位 ASCII 字符，也叫作 ISO646-US、Unicode 字符集的基本拉丁块
        ISO-8859-1      ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1
        UTF-8   8 位 UCS 转换格式
        UTF-16BE    16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序
        UTF-16LE    16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序
        UTF-16  16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
     * @author lujun.xlj@alibaba-inc.com Jan 13, 2015 4:16:10 PM
     */
    public static class EncodeGetter{
        
        private byte[] start;
        
        public EncodeGetter(byte[] start){
            if(start.length < 4){
                throw new IllegalArgumentException("file start length invlid, 4 expected!");
            }
            this.start = start;
        }
        
        public Charset getCharset(){
            
            String charsetName = null;
            if( start[0] == (byte) 0xFF && start[1] == (byte) 0xFE){
                charsetName = "UTF-16LE";
            }else if(start[0] == (byte) 0xFE && start[1] == (byte) 0xFF){
                charsetName = "UTF-16BE";
            }else if(start[0] == (byte) 0xEF && start[1] == (byte) 0xBB && start[2] == (byte) 0xBF){
                charsetName = "UTF-8";
            }else if(start[0]==(byte)0xFF && start[1]==(byte)0xFE){
                charsetName = "unicode";
            }else if(start[0]==(byte)0xFE && start[1]==(byte)0xFF){
                charsetName = "unicode big endian";
            }else{
                charsetName = "GBK";
            }
            
            Charset charset = null;
            try{
                charset = Charset.forName(charsetName);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return charset;

        }
    }
}
