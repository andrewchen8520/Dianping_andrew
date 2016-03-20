package com.jiedesui.welcomestart.entity;

/**
 * Created by sean on 2016/3/16.
 */

//对应于服务器端的ResponseIbject类
public class ResponseObject<T> {
    private String state;
    private T datas;

    public ResponseObject(){
        super();
    }

    public ResponseObject(String state, T datas) {
        this.state = state;
        this.datas = datas;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
