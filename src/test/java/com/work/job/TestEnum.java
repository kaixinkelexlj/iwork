package com.work.job;

public enum TestEnum {
    
    instA("test1", ""), instB("test1", "");
    
    private String test;
    private String source;
    
    private TestEnum(String test, String source){
        this.test = test;
        this.source = source;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    public static void main(String[] args) {
        System.out.println(TestEnum.instA.getSource());
        TestEnum.instA.setSource("mainTest");
        System.out.println(TestEnum.instA.getSource());
    }
    
    
}
