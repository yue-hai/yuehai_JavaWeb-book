package com.yuehai.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 管理员拦截
 * @author 月海
 * @create 2022/1/15 17:46
 */
public class ManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 类型转换
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // 获取 session 中 key(名称)为 user 的值
        Object user = httpServletRequest.getSession().getAttribute("user");

        // 判断用户是否登录
        if(user == null){
            // 若未登录，则跳转到登录页面
            httpServletRequest.getRequestDispatcher("/pages/user/login.jsp").forward(servletRequest, servletResponse);
        }else {
            // 若已登录，则让程序继续往下访问用户的目标资源
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
