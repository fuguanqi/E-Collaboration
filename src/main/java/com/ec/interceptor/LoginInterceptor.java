package com.ec.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //获取请求的url
        String url=request.getRequestURI();
        //判断url是否是公开地址（实际使用时将公开地址配置在文件中）
        if(url.indexOf("/user/login.action")>=0){
            return true;
        }
        HttpSession session=request.getSession();
        String username=(String) session.getAttribute("username");
        if(username!=null){
            return true;
        }
        //执行到这里表示需要进行身份认证，跳转到登陆页面
        request.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp").forward(request,response);
        return false;
    }

   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object var3, Exception var4) throws Exception{

    }
}
