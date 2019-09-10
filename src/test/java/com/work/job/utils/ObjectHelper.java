package com.work.job.utils;

import com.alibaba.fastjson.JSON;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.util.ReflectionUtils;

/**
 * @author xulujun
 * @date 2018/08/14
 */
public final class ObjectHelper {

  /**
   * 解析值
   */
  public static Object parseValue(String value, Class<?> type, java.lang.reflect.Type genericType) {
    if (value == null) {
      return null;
    }
    if (type.isPrimitive()) {
      if (Integer.TYPE.equals(type)) {
        return Integer.parseInt(value);
      } else if (Boolean.TYPE.equals(type)) {
        return Boolean.parseBoolean(value);
      } else if (Long.TYPE.equals(type)) {
        return Long.parseLong(value);
      } else if (Double.TYPE.equals(type)) {
        return Double.parseDouble(value);
      } else if (Float.TYPE.equals(type)) {
        return Float.parseFloat(value);
      } else if (Byte.TYPE.equals(type)) {
        return Byte.parseByte(value);
      } else if (Short.TYPE.equals(type)) {
        return Short.parseShort(value);
      } else if (Character.TYPE.equals(type)) {
        char[] array = value.toCharArray();
        return array.length > 0 ? array[1] : null;
      } else {
        return null;
      }
    } else if (String.class.isAssignableFrom(type)) {
      return value;
    } else {
      if (genericType != null) {
        return JSON.parseObject(value, genericType);
      } else {
        return JSON.parseObject(value, type);
      }
    }
  }

  /**
   * getFieldGeneric
   *
   * @param field field
   * @return java.lang.reflect.Type
   */
  public static java.lang.reflect.Type getFieldGeneric(Field field) {
    try {
      Object value = null;
      if (Modifier.isStatic(field.getModifiers())) {
        ReflectionUtils.makeAccessible(field);
        value = field.get(null);
      }
      Class<?> type = field.getType();
      if (type.isPrimitive()) {
        return null;
      }
      Class clz = value == null ? field.getType() : value.getClass();
      while (clz.isAnonymousClass()) {
        clz = clz.getSuperclass();
      }
      java.lang.reflect.Type rawType = clz;
      java.lang.reflect.Type genericType = field.getGenericType();
      try {
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        return new TypeReference(rawType,
            parameterizedType.getActualTypeArguments());
      } catch (ClassCastException ex) {
        return genericType;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  private ObjectHelper() {
  }

  public static class TypeReference implements ParameterizedType {

    private Type rawType;
    private Type[] actualTypeArguments;

    public TypeReference(Type rawType, Type[] actualTypeArguments) {
      this.rawType = rawType;
      this.actualTypeArguments = actualTypeArguments;
    }

    public Type[] getActualTypeArguments() {
      return this.actualTypeArguments;
    }

    public Type getRawType() {
      return this.rawType;
    }

    public Type getOwnerType() {
      return null;
    }
  }

}
