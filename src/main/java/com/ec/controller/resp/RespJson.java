package com.ec.controller.resp;

import net.sf.json.JSONObject;

public abstract class RespJson {
    public static final String  SUCCESS = "("+"{\"state\""+":"+"\"success\"}"+")";
    public static final String  FALSE = "("+"{\"state\""+":"+"\"false\"}"+")";
    public static String getString(String callback, int i){
        if(i!=0) {
            if(callback == null){
                return RespJson.SUCCESS;
            }else {
                return callback+RespJson.SUCCESS;
            }
        }else if (callback == null) {
            return RespJson.FALSE;
        } else {
            return callback +RespJson.FALSE;
        }
    }

    public static String ifJsonp(String callback,Object data){
        if(callback==null){
            System.out.println("非跨域请求");
            return JSONObject.fromObject(data).toString();
        }else {
            System.out.println("跨域请求");
            return callback+"("+JSONObject.fromObject(data).toString()+")";//异域请求要求callback+"("+数据+")"
        }
    }

}