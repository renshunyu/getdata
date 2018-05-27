package com.asia.Result;

/**
 * Created by renshunyu on 2018/5/26.
 */
public class NameValue {
    String name;
    Integer value;

    public NameValue(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
