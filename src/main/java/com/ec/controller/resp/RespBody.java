package com.ec.controller.resp;


import java.util.List;

public class RespBody {
    //响应状态
    private Integer status;

    //响应消息
    private String msg;

    //响应数据
    private List<Object> data;

    //data中的成员个数
    private Integer num;

    //空参的构造函数
    public RespBody() {

    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    //只含data的构造函数
    public RespBody(List data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;

        this.num = data.size();
    }

    //包含所有参数的构造函数
    public RespBody(Integer status, String msg, List data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.num = data.size();
    }

    //构建响应体（返回data，其他参数默认）
    public static RespBody build(List data) {
        return new RespBody(data);
    }

    //构建响应体（返回data，其他参数自定义）
    public static RespBody build(Integer status, String msg, List data) {
        return new RespBody(status, msg, data);
    }

    //构建响应体（不返回data，其他参数自定义）
    public static RespBody build(Integer status, String msg) {
        return new RespBody(status, msg, null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
