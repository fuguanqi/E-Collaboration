package com.ec.filter;

import net.minidev.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import com.ec.jwt.Jwt;
import com.ec.jwt.TokenState;


@WebFilter(urlPatterns="/*")
public class TokenFilter  implements Filter {


//    @Override
    public void doFilter(ServletRequest argo, ServletResponse arg1,
                         FilterChain chain ) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) argo;
        HttpServletResponse response=(HttpServletResponse) arg1;
//		response.setHeader("Access-Control-Allow-Origin", "*");
        if(request.getRequestURI().endsWith("/login")||request.getRequestURI().endsWith("/register")){
            //登陆接口不校验token，直接放行
            chain.doFilter(request, response);
            return;
        }
        //其他API接口一律校验token
        System.out.println("开始校验token");
        //从请求头中获取token
        String token=request.getHeader("token");
        Map<String, Object> resultMap=Jwt.validToken(token);
        TokenState state=TokenState.getTokenState((String)resultMap.get("state"));
        switch (state) {
            case VALID:
                //取出payload中数据,放入到request作用域中
//                request.setAttribute("data", resultMap.get("data"));
                System.out.println(resultMap.get("uid"));
                //放行
                chain.doFilter(request, response);
                break;
            case EXPIRED:
            case INVALID:
                System.out.println("无效token");
                //token过期或者无效，则输出错误信息返回给ajax
                JSONObject outputMSg=new JSONObject();
                outputMSg.put("success", false);
                outputMSg.put("msg", "您的token不合法或者过期了，请重新登陆");
                output(outputMSg.toJSONString(), response);
                break;
        }


    }


    public void output(String jsonStr,HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=UTF-8;");
        PrintWriter out = response.getWriter();
//		out.println();
        out.write(jsonStr);
        out.flush();
        out.close();

    }

//    @Override
    public void init(FilterConfig arg0) throws ServletException {
        System.out.println("token过滤器初始化了");
    }

//    @Override
    public void destroy() {

    }

}

