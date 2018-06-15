package com.work.aop;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {
    public void say(){
        System.out.println("hello world");
    }
    
    public void say(String name1, String name2){
        System.out.println("hello world, " + name1 +"," + name2 + "!");
    }
    
    @TairLock(clazz = HelloWorldService.class)
    public void sayOneByOne(){
        System.out.println("sayOneByOne!");
    }
}
