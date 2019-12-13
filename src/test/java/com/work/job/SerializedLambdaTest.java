package com.work.job;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun 2019/12/12.
 */
public class SerializedLambdaTest {

  @FunctionalInterface
  public interface Fn<T> extends Serializable {

    Object apply(T obj);
  }

  @lombok.Data
  public class Data {

    private Integer id;
    private String name;
    private boolean ok;
  }

  public static class FnConverter<T> {

    public String fn2String(Fn<T> fn) {
      try {
        Method method = fn.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
        String methodName = serializedLambda.getImplMethodName().replace("get", "");
        return methodName2Property(methodName);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }

    private String methodName2Property(String methodName){
      if(StringUtils.isBlank(methodName)){
        return methodName;
      }
      if(methodName.startsWith("get") || methodName.startsWith("set")){
        return Introspector.decapitalize(methodName.substring(3));
      }
      if(methodName.startsWith("is")){
        return Introspector.decapitalize(methodName.substring(2));
      }
      return Introspector.decapitalize(methodName);
    }

  }

  @Test
  public void name() {

    String name = new FnConverter<Data>().fn2String(Data::getId);
    System.out.println(name);
    String ok = new FnConverter<Data>().fn2String(Data::isOk);
    System.out.println(ok);

  }
}
