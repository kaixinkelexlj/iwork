package com.fun.classload;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    interface IHello {
        void sayHello();
    }
    static class Hello implements IHello{

        @Override
        public void sayHello() {
            // TODO Auto-generated method stub
            System.out.println("hello world");
        }
        
    }
    
    static class DynamicProxy implements InvocationHandler{
        
        Object oriObj;
        public Object bind(Object obj){
            this.oriObj = obj;
            return Proxy.newProxyInstance(oriObj.getClass().getClassLoader(), 
                     oriObj.getClass().getInterfaces(), this);
            //return obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           System.out.println("welcome,");
           //return method.invoke(oriObj, args);//死循环
           return method.invoke(oriObj, args);
        }
        
    }
    
    public static void main(String[] args) {
        IHello hello = (IHello)new DynamicProxy().bind(new Hello());
        hello.sayHello();
    }
}
