package com.fun.oio;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Request {

    private String target;
    private Map<String, String> parameters = new HashMap<String, String>();

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameter(String key) {
        return getParameters().get(key);
    }

    public String getParameter(String key, String defaultValue) {
        String value = getParameter(key);
        return StringUtils.isBlank(value) ? defaultValue : value;
    }

    public void addParameter(String key, String value) {
        this.parameters.put(key, value);
    }
}