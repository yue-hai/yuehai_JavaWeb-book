package com.yuehai.web;

import com.yuehai.pojo.Book;
import com.yuehai.pojo.Page;
import com.yuehai.service.BookService;
import com.yuehai.service.impl.BookServiceImpl;
import com.yuehai.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 月海
 * @create 2022/1/11 17:42
 */

// 前台
public class ClientBookServlet extends BaseServlet {
    // Servlet 的实现依赖于 Service
    private BookService bookService = new BookServiceImpl();

    // 分页
    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数 pageNo 和 pageSiz
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);

        // 2、调用 BookService.page(pageNo，pageSize) 获取 Page 对像
        Page<Book> page = bookService.page(pageNo,pageSize);

        // clientBookServlet 的请求地址
        page.setUrl("client/clientBookServlet?action=page");

        // 3、保存 Page 对象到 Request 域中
        request.setAttribute("page",page);

        // 4、请求转发到 pages/manager/index.jsp 页面
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request,response);
    }

    // 依据价格搜索
    protected void pageByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数 pageNo 和 pageSiz
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        // 依据价格搜索的最小值，默认值为 0
        int min = WebUtils.parseInt(request.getParameter("min"), 0);
        // 依据价格搜索的最大值，默认值为 2147483647
        int max = WebUtils.parseInt(request.getParameter("max"), Integer.MAX_VALUE);

        // 2、调用 BookService.page(pageNo，pageSize) 获取 Page 对像
        Page<Book> page = bookService.pageByPrice(pageNo,pageSize,min,max);

        // 创建 StringBuilder 字符串，值为请求分页条的 url
        StringBuilder sb = new StringBuilder("client/clientBookServlet?action=pageByPrice");
        // 如果有最小价格的参数，追加到请求参数中
        if(request.getParameter("min") != null){
            sb.append("&min=").append(request.getParameter("min"));
        }
        // 如果有最大价格的参数，追加到请求参数中
        if(request.getParameter("max") != null){
            sb.append("&max=").append(request.getParameter("max"));
        }

        // clientBookServlet 的请求地址
        page.setUrl(sb.toString());

        // 3、保存 Page 对象到 Request 域中
        request.setAttribute("page",page);

        // 4、请求转发到 pages/manager/index.jsp 页面
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request,response);
    }

    // 不用再写 doPost 方法，因为继承的父类 BaseServlet 中已有实现方法
}
