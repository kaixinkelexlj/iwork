package com.work.job.bean;

public class Son extends Parent{
    public void test1(){
        System.out.println("test1 from son");
    }
    public void testSon(){
        System.out.println("i am son");
    }
    
    private String s3;

    public String getS3() {
        return s3;
    }
    public void setS3(String s3) {
        this.s3 = s3;
    }
    
}
