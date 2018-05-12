package com.asia.Result;

/**
 * Created by renshunyu on 2018/5/10.
 */
public class JsonSerie {
    Double [] data=null;
    String name="计划";
    String type="line";
    String stack="剩余计划";

    public JsonSerie() {
    }

    public JsonSerie(String name) {
        this.name = name;
    }

    public JsonSerie(String name, String stack) {
        this.name = name;
        this.stack = stack;
    }

    public Double[] getData() {
        return data;
    }

    public void setData(Double[] data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }
}
