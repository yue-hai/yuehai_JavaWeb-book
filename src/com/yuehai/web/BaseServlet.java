package com.yuehai.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author 月海
 * @create 2022/1/7 22:22
 */
public abstract class BaseServlet extends HttpServlet {

    /**
     * 若是提交的请求是 get 请求，则用 get 请求调用 post 请求
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 防止表单提交的是get方法，导致异常，所以要在 get 请求中调用 post 请求
        doPost(request, response);
    }

    /**
     * 使用反射处理总的用户请求
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 解决添加数据到数据库乱码的问题
        request.setCharacterEncoding("UTF-8");
        // 解决响应的中文乱码
        response.setContentType("text/html; charset=UTF-8");

        // 获取表单中 name 为 action 的 值（value）
        String action = request.getParameter("action");

        try {
            // 获取 action 业务鉴别字符串，获取相应的业务方法反射的对象
            Method method = this.getClass().getDeclaredMethod(action,HttpServletRequest.class,HttpServletResponse.class);

            // 调用目标业务的方法
            method.invoke(this,request,response);

        } catch (Exception e) {
            e.printStackTrace();

            // 把异常抛给 Filter 过滤器
            throw new RuntimeException(e);
        }

    }

}
