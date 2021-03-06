package com.fun.classload;

public class StaticField {
    private String ok;
    
    public String getOk() {
        return ok == null ? "" : ok;
    }
    
    public void setOk(String ok) {
        this.ok = ok;
    }
    public static class A{
        public static int a = 100;
        static{
            System.out.println(a);
            b = 2000;
            //System.out.println(b);//静态块只能访问之前定义的静态变量，不能访问之后的，但可以赋值；
        }
        static int b = 0;
        static {
            //b = 2000;
            System.out.println(b);
        }
    }
    public StaticField(String ok){
        this.ok = ok;
    }
    public static void main(String[] args) {
        
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof StaticField))
            return false;
        StaticField t = (StaticField) obj;
        return t.getOk().equals(getOk());
    }
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return getOk().hashCode();
    }
}
