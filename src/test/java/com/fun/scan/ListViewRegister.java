/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.fun.scan;

import com.fun.scan.listview.ListView;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fun.scan.listview.ListView;


/**
 * 
 * @author lujun.xlj
 * @version $Id: ListViewScanner.java, v 0.1 Nov 17, 2015 5:19:20 PM lujun.xlj Exp $
 */
public class ListViewRegister implements BeanDefinitionRegistryPostProcessor, BeanPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {
    
    /** 
     * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    /** 
     * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
     */
    @Override
    public void setBeanName(String name) {
    }

    /** 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(basePackage, "basePackage can not be null");
    }

    /** 
     * @see org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry(org.springframework.beans.factory.support.BeanDefinitionRegistry)
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ListviewScanner scanner = new ListviewScanner(registry);
        scanner.registerFilters();
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    
    /**
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object,
     * java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object,
     * java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ListView) {
            com.fun.scan.annotation.ListView annotation = bean.getClass().getAnnotation(
                com.fun.scan.annotation.ListView.class);
            if (annotation != null) {
                ListView view = (ListView) bean;
                uriMap.put(annotation.url(), view);
                nameMap.put(beanName, view);
            }
        }
        return bean;
    }

    private String                basePackage;
    private Map<String, ListView> uriMap  = new HashMap<>();
    private Map<String, ListView> nameMap = new HashMap<>();

    public String getBasePackage() {
        return basePackage;
    }
    
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
    
    public Map<String, ListView> getUriMap() {
        return uriMap;
    }

    public void setUriMap(Map<String, ListView> uriMap) {
        this.uriMap = uriMap;
    }

    public Map<String, ListView> getNameMap() {
        return nameMap;
    }

    public void setNameMap(Map<String, ListView> nameMap) {
        this.nameMap = nameMap;
    }

}
