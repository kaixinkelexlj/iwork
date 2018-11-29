package com.work.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xulujun
 * @date 2018/08/30
 */
@Configuration
public class RegexpAopConfig {

  @Bean
  public RegexpMethodPointcutAdvisor getRegexpAdvisor() {
    RegexpMethodPointcutAdvisor advisor = new RegexpMethodPointcutAdvisor();
    advisor.setPattern("com.work.aop\\..*ServiceProvider\\..*");
    advisor.setAdvice(new MethodInterceptor() {
      @Override
      public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("cut by RegexpMethodPointcutAdvisor");
        return methodInvocation.proceed();
      }
    });
    return advisor;
  }

}
