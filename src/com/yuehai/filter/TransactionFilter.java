package com.yuehai.filter;

import com.yuehai.utils.JDBCUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * 使用Filter过滤器进行事务管理
 * @author 月海
 * @create 2022/1/15 22:14
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            // 程序继续往下访问用户的目标资源
            filterChain.doFilter(servletRequest, servletResponse);

            // 没有异常，提交事务
            JDBCUtils.commitAndClose();
        } catch (Exception e) {
            // 若是有异常，回滚事务
            JDBCUtils.rollbackAndClose();

            e.printStackTrace();

            // 将异常抛出给 Tomcat 服务器，使其知道错误类型
            throw new RuntimeException();
        }
    }

    @Override
    public void destroy() {

    }
}
