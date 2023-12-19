package com.work.job;

import com.alibaba.fastjson.JSON;
import com.work.AbstractTest;
import com.work.job.utils.ObjectHelper;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @author xulujun
 * @date 2019/02/13
 */
public class ObjectHelperTest extends AbstractTest {


  @Test
  public void test() throws Exception {
    Stream.of(MyObj.class.getDeclaredFields())
        .forEach(field -> {
          String name = field.getName();
          Type fieldGeneric = ObjectHelper.getFieldGeneric(field);
          Class<?> clazz = field.getType();
          System.out.println(name + "," + clazz.getName() + "," + (fieldGeneric == null ? "<null>"
              : JSON.toJSONString(fieldGeneric)));
        });

  }

  private MyObj getDemoObj() {
    MyObj myObj = new MyObj();
    myObj.setList(Arrays.asList("hello", "world"));
    Map<Integer, Boolean> map = new HashMap<>();
    map.put(1, true);
    myObj.setMap(map);
    myObj.setBoxedIntVal(new Integer(100));
    myObj.setDt(new Date());
    myObj.setStr("fun");
    return myObj;
  }

  public static class MyObj {

    private List<String> list;
    private Map<Integer, Boolean> map;
    private String str;
    private long longVal;
    private Integer boxedIntVal;
    private Date dt;

    public List<String> getList() {
      return list;
    }

    public void setList(List<String> list) {
      this.list = list;
    }

    public Map<Integer, Boolean> getMap() {
      return map;
    }

    public void setMap(Map<Integer, Boolean> map) {
      this.map = map;
    }

    public String getStr() {
      return str;
    }

    public void setStr(String str) {
      this.str = str;
    }

    public long getLongVal() {
      return longVal;
    }

    public void setLongVal(long longVal) {
      this.longVal = longVal;
    }

    public Integer getBoxedIntVal() {
      return boxedIntVal;
    }

    public void setBoxedIntVal(Integer boxedIntVal) {
      this.boxedIntVal = boxedIntVal;
    }

    public Date getDt() {
      return dt;
    }

    public void setDt(Date dt) {
      this.dt = dt;
    }
  }

}
