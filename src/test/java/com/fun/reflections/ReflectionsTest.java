package com.fun.reflections;

import com.alibaba.fastjson.JSON;
import com.fun.reflections.annotations.ApolloNamespace;
import com.fun.reflections.annotations.ApolloNamespaces;
import com.fun.reflections.annotations.TestBean;
import com.fun.reflections.testmodel.BInterface;
import com.fun.reflections.testmodel.ConfigBean;
import com.fun.reflections.testmodel.FunConfig;
import com.fun.reflections.testmodel.MysqlConfigBean;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

/**
 * @author xulujun 2019/10/09
 */
public class ReflectionsTest {


  @SuppressWarnings("unchecked")
  @Test
  public void testReflections() throws Exception {
    Reflections reflections = new Reflections("com.fun.reflections.testmodel",
        new SubTypesScanner(),
        new TypeAnnotationsScanner());
    Set<Class<?>> classes = reflections.getTypesAnnotatedWith(TestBean.class);
    classes.forEach(clz -> {
      /*Set<Field> fields = new Reflections(clz, new FieldAnnotationsScanner())
          .getFieldsAnnotatedWith(TestValue.class);
      if (CollectionUtils.isNotEmpty(fields)) {
        fields.forEach(f -> System.out.println(f.getName()));
      }*/
      Set<ApolloNamespace> namespaces = new HashSet<>();
      Set<Annotation> annotationSet = ReflectionUtils.getAllAnnotations(clz);
      if (!CollectionUtils.isEmpty(annotationSet)) {
        annotationSet.forEach(ns -> {
          if (ns instanceof ApolloNamespaces) {
            ApolloNamespace[] arr = ((ApolloNamespaces) ns).value();
            namespaces.addAll(Stream.of(arr).collect(Collectors.toList()));
          }
          if (ns instanceof ApolloNamespace) {
            namespaces.add((ApolloNamespace) ns);
          }

        });
      }
      annotationSet = ReflectionUtils.getAllAnnotations(ApolloNamespace.class);
      if (!CollectionUtils.isEmpty(annotationSet)) {

      }
      namespaces
          .forEach(ns -> System.out.println(JSON.toJSONString(ns.profile()) + ":" + ns.value()));
    });
  }

  @Test
  public void testGetSuper() throws Exception {
    System.out.println(FunConfig.class.getSuperclass().getName());

    System.out.println(JSON.toJSONString(MysqlConfigBean.class.getInterfaces()));
    System.out.println(JSON.toJSONString(ConfigBean.class.getInterfaces()));
    System.out.println(JSON.toJSONString(BInterface.class.getInterfaces()));
    //System.out.println(BInterface.class.getSuperclass().getName());

    for (Class<?> clz = MysqlConfigBean.class; clz != null && clz != Object.class;
        clz = clz.getSuperclass()) {
      System.out.println(clz.getName());
    }

  }


}
