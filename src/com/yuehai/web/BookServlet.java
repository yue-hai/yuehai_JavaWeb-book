package com.yuehai.web;

import com.yuehai.pojo.Book;
import com.yuehai.pojo.Page;
import com.yuehai.service.BookService;
import com.yuehai.service.impl.BookServiceImpl;
import com.yuehai.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 月海
 * @create 2022/1/8 21:25
 */

// 后台
public class BookServlet extends BaseServlet {
    // Servlet 的实现依赖于 Service
    private BookService bookService = new BookServiceImpl();

    // 添加
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 添加图书后要跳转到最后一页，同时要防止添加后多了一页，所以给传进来的总页码 + 1
        // 添加图书传递的总页码的名称为：pageNo，而不是 pageTotal，是为了方便
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 0);
        pageNo = pageNo + 1;

        // 1、获取请求的参数==封装成为 Book 对象
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        // 2、调用 BookService.addBook()保存图书
        bookService.addBook(book);
        // 3、跳到图书列表页面，请求重定向（不可以传递数据）
        //    request.getContextPath()：工程的根目录下的 web 目录
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);
    }

    // 删除
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数 id，并通过自己封装的 parseInt 方法将字符串转换为 int 类型
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 2、调用 bookService.deleteBookById();删除图书
        bookService.deleteBookById(id);
        // 3、重定向回图书列表管理页面，页码为传递进来的参数，不用改，因为前面设置的超出最大页码会改为最大页码
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + request.getParameter("pageNo"));
    }

    // 修改
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数==封装成为 Book 对象
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        // 2、调用 BookService.updateBook( book );修改图书
        bookService.updateBook(book);
        // 3、重定向回图书列表管理页面，页码为传递进来的参数
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + request.getParameter("pageNo"));
    }

    // 通过 id 查询一本图书
    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数图书编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 2、调用 bookService.queryBookById 查询图书
        Book book = bookService.queryBookById(id);
        // 3、保存到图书到 Request 域中
        request.setAttribute("book",book);
        // 4、请求转发到。pages/manager/book_edit.jsp 页
        request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request,response);
    }

    // 查询全部图书
    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、通过 BookService 查询全部图书
        List<Book> books = bookService.queryBooks();
        // 2、把全部图书保存到 Request 域中
        request.setAttribute("books",books);
        // 3、请求转发到/pages/manager/book_manager.jsp 页
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);
    }

    // 分页
    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数 pageNo 和 pageSiz
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);

        // 2、调用 BookService.page(pageNo，pageSize) 获取 Page 对像
        Page<Book> page = bookService.page(pageNo,pageSize);

        // bookServlet 的请求地址
        page.setUrl("manager/bookServlet?action=page");

        // 3、保存 Page 对象到 Request 域中
        request.setAttribute("page",page);

        // 4、请求转发到 pages/manager/book_manager.jsp 页面
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);
    }

    // 不用再写 doPost 方法，因为继承的父类 BaseServlet 中已有实现方法
}
