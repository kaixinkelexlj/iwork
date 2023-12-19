package com.fun.reflections.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xulujun
 * @date 2018/08/13
 */
@Repeatable(ApolloNamespaces.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ApolloNamespace {

  String value();

  String[] profile() default {};
}
