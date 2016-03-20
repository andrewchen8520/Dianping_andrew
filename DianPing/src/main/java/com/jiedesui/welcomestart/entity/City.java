package com.jiedesui.welcomestart.entity;

/**
 * Created by sean on 2016/3/16.
 * 对应于数据库中的city表信息
 */
public class City {
    private String id;
    private String name;
    private String sortKey;

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

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
}
