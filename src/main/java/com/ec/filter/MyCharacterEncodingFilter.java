package com.ec.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyCharacterEncodingFilter implements Filter {
    //解码方式
    private String encoding;

    private boolean forceGet = false;

    private boolean forceRequestEncoding = false;

    private boolean forceResponseEncoding = false;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    public void destroy() {

    }
}
