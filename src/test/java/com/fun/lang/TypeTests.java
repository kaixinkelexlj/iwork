package com.fun.lang;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;

/**
 * @author xulujun@kuaishou.com
 * Created on 2023-12-27
 */
public class TypeTests {

    static class SimpleClass {
        public Map<String, Object> getMap() {
            return null;
        }

        public GenericTypeCustom genericTypeCustom() {
            return null;
        }
    }

    static class Generic<A, B> {

    }

    static class GenericTypeCustom extends Generic<String, Integer> {

    }

    @Test
    public void test2() throws Exception {
        Method method = ReflectionUtils.findMethod(SimpleClass.class, "getMap");
        if (method != null) {
            System.out.println("1-->:" + ResolvableType.forType(method.getReturnType()));
            Arrays.stream(method.getReturnType().getTypeParameters())
                    .forEach(type -> {
                        ResolvableType resolvableType = ResolvableType.forType(type);
                        System.out.println(resolvableType);
                    });
            System.out.println(GenericTypeResolver.resolveReturnType(method, SimpleClass.class));
        }
        System.out.println("========");
        method = ReflectionUtils.findMethod(SimpleClass.class, "genericTypeCustom");
        if (method != null) {
            System.out.println("1-->:" + ResolvableType.forType(method.getReturnType()));
            Arrays.stream(method.getReturnType().getTypeParameters())
                    .forEach(type -> {
                        ResolvableType resolvableType = ResolvableType.forType(type);
                        System.out.println(resolvableType);
                    });
            System.out.println(GenericTypeResolver.resolveReturnType(method, SimpleClass.class));
        }
    }

    @Test
    public void test() throws Exception {
        SimpleClass obj1 = new SimpleClass();
        Generic<String, Integer> obj2 = new Generic<>();
        /*Type javaType = obj2.getClass();
        System.out.println(((ParameterizedType) javaType).getActualTypeArguments());*/
        ResolvableType resolvableType = ResolvableType.forType(obj2.getClass());
        System.out.println(resolvableType.getGeneric(0));
        GenericTypeCustom obj3 = new GenericTypeCustom();
        resolvableType = ResolvableType.forType(obj3.getClass());
        System.out.println(resolvableType.getGeneric().getType());
    }


}
