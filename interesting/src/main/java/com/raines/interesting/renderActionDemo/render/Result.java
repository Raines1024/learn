package com.raines.interesting.renderActionDemo.render;

public class Result {
    String success; //"0":失败，"1":成功
    String msg;      //服务器返回的消息
    Object data;     //服务器返回的数据

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
