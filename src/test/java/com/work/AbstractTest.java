package com.work;

import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AbstractTest {

    private static final String SYS_OUT_PREFIX = "#################";

    public static String stringIt(Object obj) {
        if (obj == null) {
            return "<null>";
        }
        return ToStringBuilder.reflectionToString(obj, ToStringStyle.DEFAULT_STYLE);
    }

    public static void json(Object obj) {
        String result = "";
        if (obj == null) {
            result = "<null>";
        } else if (obj instanceof Collection<?>) {
            Collection<?> list = (Collection<?>)obj;
            if (list.size() == 0) {
                result = "<empty>";
            }
            result = toJsonString(obj);
            System.out.println(SYS_OUT_PREFIX + "!!size:" + list.size() + "!!\ncontent:" + result);
        } else {
            result = toJsonString(obj);
            System.out.println(SYS_OUT_PREFIX + "!!\ncontent:" + result);
        }

    }

    public static void p(Object o) {
        if (o != null && o instanceof String) {
            System.out.println(o);
            return;
        }
        System.out.println(SYS_OUT_PREFIX + (o == null ? "<null>" : toJsonString(o)));
    }

    public static void plist(Collection<?> list) {
        String result = "";
        if (list == null) {
            result = "<null>";
        } else if (list.size() == 0) {
            result = "<empty>";
        } else {
            result = toJsonString(list);
        }
        System.out.println(SYS_OUT_PREFIX + "!!size:" + ((null == list) ? 0 : list.size()) + "!!\ncontent:" + result);
    }

    public static void ps(Object o) {
        if (o != null && o instanceof String) {
            System.out.println(o);
            return;
        }
        System.out.println(SYS_OUT_PREFIX + (o == null ? "<null>" : stringIt(o)));
    }

    public static void pslist(Collection<?> list) {
        String result = "";
        if (list == null) {
            result = "<null>";
        } else if (list.size() == 0) {
            result = "<empty>";
        } else {
            StringBuffer buff = new StringBuffer();
            for (Object obj : list) {
                buff.append(stringIt(obj)).append("\r\n");
            }
            result = buff.toString();
        }
        System.out.println(SYS_OUT_PREFIX + "!!size:" + ((null == list) ? 0 : list.size()) + "!!\ncontent:" + result);
    }

    private static String toJsonString(Object obj) {
        if (obj == null) {
            return "<null>";
        }
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception ex) {
            return "<error>";
        }
    }

}
