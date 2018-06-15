/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.fun.scan;

import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.fun.scan.annotation.ListView;


/**
 * 
 * @author lujun.xlj
 * @version $Id: ListviewScanner.java, v 0.1 Nov 17, 2015 5:27:53 PM lujun.xlj Exp $
 */
public class ListviewScanner  extends ClassPathBeanDefinitionScanner {

    /**
     * @param registry
     */
    public ListviewScanner(BeanDefinitionRegistry registry){
        super(registry);
    }
    
    public void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(ListView.class));
    }
    
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            logger.warn("No listview was found in '" + Arrays.toString(basePackages)
                        + "' package. Please check your configuration.");
        } else {
            for (BeanDefinitionHolder holder : beanDefinitions) {
                GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

                if (logger.isDebugEnabled()) {
                    logger.debug("Creating listview with name '" + holder.getBeanName() + "' and '"
                                 + definition.getBeanClassName() + "' listview");
                }
                
                System.out.println(definition.toString());
                
            }
        }

        return beanDefinitions;
    }

}
