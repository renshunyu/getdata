package com.asia.Result;

/**
 * Created by renshunyu on 2018/5/12.
 */
public class jsonJira {
    String type;
    String id;
    Integer day;
    String name;


    public jsonJira() {
    }

    public jsonJira(String id, String name, Integer day, String type) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
