package com.ec.filter;

import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyEncodingFilter extends OncePerRequestFilter {

    //解码方式
    private String encoding;

    private boolean forceGet = false;

    private boolean forceRequestEncoding = false;

    private boolean forceResponseEncoding = false;


    public MyEncodingFilter() {
    }

    public MyEncodingFilter(String encoding) {
        this(encoding,false, false);
    }

    public MyEncodingFilter(String encoding,boolean forceGet) {
        this(encoding,forceGet, false);
    }

    public MyEncodingFilter(String encoding,boolean forceGet, boolean forceEncoding) {
        this(encoding,forceGet,forceEncoding, forceEncoding);
    }

    public MyEncodingFilter(String encoding,boolean forceGet,boolean forceRequestEncoding, boolean forceResponseEncoding) {
        Assert.hasLength(encoding, "Encoding must not be empty");
        this.forceGet = forceGet;
        this.encoding = encoding;
        this.forceRequestEncoding = forceRequestEncoding;
        this.forceResponseEncoding = forceResponseEncoding;
    }


    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }


    public String getEncoding() {
        return this.encoding;
    }

    public Boolean isForceGet() {
        return forceGet;
    }

    public void setForceGet(Boolean forceGet) {
        this.forceGet = forceGet;
    }

    public void setForceEncoding(boolean forceEncoding) {
        this.forceRequestEncoding = forceEncoding;
        this.forceResponseEncoding = forceEncoding;
    }

    public void setForceRequestEncoding(boolean forceRequestEncoding) {
        this.forceRequestEncoding = forceRequestEncoding;
    }


    public boolean isForceRequestEncoding() {
        return this.forceRequestEncoding;
    }


    public void setForceResponseEncoding(boolean forceResponseEncoding) {
        this.forceResponseEncoding = forceResponseEncoding;
    }


    public boolean isForceResponseEncoding() {
        return this.forceResponseEncoding;
    }




    protected void doFilterInternal(final HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        HttpServletRequest requestProxy = request;
        if(encoding != null){
            if(isForceRequestEncoding()|| request.getCharacterEncoding() == null){
                //创建代理对象
                requestProxy = (HttpServletRequest)Proxy.newProxyInstance(HttpServletRequest.class.getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //只改变request对象的getParameter方法
                        if ("getParameter".equals(method.getName())) {
                            //获得请求方式(get和post请求处理的方式不一样)
                            String m = request.getMethod();
                            if ("get".equalsIgnoreCase(m) && isForceGet()) {
                                String s = (String) method.invoke(request, args);//用反射的方式执行request.getParameter(args)
                                return new String(s.getBytes("iso8859-1"), encoding);
                            } else if ("post".equalsIgnoreCase(m)) {
                                request.setCharacterEncoding(encoding);
                                return method.invoke(request, args);
                            } else {
                                return method.invoke(request, args);
                            }
                        }
                        //不需要改变的方法直接反射执行
                        return method.invoke(request, args);
                    }
                });
            }
            if(isForceResponseEncoding()){
                //解决response乱码
                response.setCharacterEncoding(encoding);//设置服务器端的编码
                response.setHeader("Content-type", "text/html;charset=UTF-8");//通知浏览器服务器发送的数据格式
            }
        }

        //放行
        filterChain.doFilter(requestProxy,response);
    }

}
