package com.ec.controller.resp;
/*
 * 自定义用户响应结构
 */
public class RespUser {

    //响应状态
    private Integer status;

    //响应消息
    private String msg;

    //响应数据
    private Object data;

    //token
    private String token;


    //空参的构造函数
    public RespUser() {

    }

    //只含data的构造函数
    public RespUser(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    //包含所有参数的构造函数
    public RespUser(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public void setToken(String token) {
        this.token = token;
    }


    //构建响应体（返回data，其他参数默认）
    public static RespUser build(Object data) {
        return new RespUser(data);
    }

    //构建响应体（返回data，其他参数自定义）
    public static RespUser build(Integer status, String msg, Object data) {
        return new RespUser(status, msg, data);
    }

    //构建响应体（不返回data，其他参数自定义）
    public static RespUser build(Integer status, String msg) {
        return new RespUser(status, msg, null);
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

    public Object getData() {
        return data;
    }

    public String getToken() {
        return token;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
