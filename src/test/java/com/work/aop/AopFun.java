package com.work.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class AopFun {
    
    @Pointcut("execution (* com..*Service.*(..))")
    //@Pointcut("within(com.work.aop..xxx*)")
    public void helloWorldService(){}
    
    @Pointcut("execution (* com.work.aop.HelloWorldService.*(..)) && args(..,name2)")
    public void name2(String name2){}
    
    
    @Before("helloWorldService()")
    public void say() throws Throwable{
        System.out.println("helloworld aop before1");
    }
    
    @After("name2(name2)")
    public void name2Say(String name2) throws Throwable{
        System.out.println("name2 is " + name2);
    }
    
    @Around("helloWorldService()")
    public void say(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("helloworld aop before");
        pjp.proceed();
        System.out.println("helloworld aop ok");
    }
    
    //@Before(value = "execution (* com.work.aop.HelloWorldService.*(..)) && @annotation(com.work.aop.TairLock) && target(bean) && @annotation(tairLock)", argNames="bean,tairLock")
    @Before(value = "@annotation(com.work.aop.TairLock) && target(bean) && @annotation(tairLock)", argNames="bean,tairLock")
    public void lockAop(Object bean, TairLock tairLock) throws Throwable{
        System.out.println("TairLock:" + tairLock.clazz().getSimpleName());
    }
}
