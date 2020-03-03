package com.filter;


import com.Util.RedisUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Creator Ming
 * @Time 2019/11/17
 * @Other IP访问记录
 */
public class ipFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        RedisUtil.setAccessRecord(request);
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
