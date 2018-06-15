package com.fun.annotation;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ReflectionUtils;

public class CustomPostProcessor implements BeanPostProcessor, PriorityOrdered{

    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                Yuanbao son = field.getAnnotation(Yuanbao.class);
                if(son != null){
                    field.setAccessible(true);
                    field.set(bean, "I love you, papa");
                }
            }
        });
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        return bean;
    }
    
}
