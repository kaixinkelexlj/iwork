package com.fun.classload;

/**
 * 静态分派
 * 动态分派就是重载和多态
 * @author XLJ Jun 11, 2014 10:40:41 AM
 */
public class StaticDispatch {
    static abstract class Human{
        
    }
    static class Man extends Human{
        
    }
    static class Woman extends Human{
        
    }
    public void sayHello(Human human){
        System.out.println("hello, human");
    }
    public void sayHello(Man human){
        System.out.println("hello, man");
    }
    public void sayHello(Woman human){
        System.out.println("hello, woman");
    }
    public static void main(String[] args) {
        StaticDispatch test = new StaticDispatch();
        Human man = new Man();
        Human woman = new Woman();
        test.sayHello(man);
        test.sayHello(woman);
        test.sayHello((Man)man);
        test.sayHello((Woman)woman);
    }
}
