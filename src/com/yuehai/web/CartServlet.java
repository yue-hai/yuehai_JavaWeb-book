package com.yuehai.web;

import com.google.gson.Gson;
import com.yuehai.pojo.Book;
import com.yuehai.pojo.Cart;
import com.yuehai.pojo.CartItem;
import com.yuehai.service.BookService;
import com.yuehai.service.impl.BookServiceImpl;
import com.yuehai.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 月海
 * @create 2022/1/13 21:02
 */
public class CartServlet extends BaseServlet {
    // Servlet 的实现依赖于 Service
    private BookService bookService = new BookServiceImpl();

    /**
     * 使用 AJAX 加入购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxAddItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数：商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 调用 bookService.queryBookById(id):Book 得到图书的信息
        Book book = bookService.queryBookById(id);
        // 把图书信息，转换成为 CartItem 商品项
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());
        // 从 session 中获取购物车，并放入创建的购物车对象（cart）中
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 判断 session 中有没有购物车
        if(cart == null){
            // 如果没有，则创建购物车对象，并将数据放到购物车 session 中
            cart = new Cart();
            request.getSession().setAttribute("cart",cart);
        }
        // 调用 Cart 中的 addItem(CartItem) 方法，添加商品项
        cart.addItem(cartItem);

        // 将最后一个添加到购物车的商品名称放到购物车 session 中
        request.getSession().setAttribute("lastName",cartItem.getName());

        // 使用 AJAX 返回购物车总的商品数量和最后一个添加的商品名称
        // 创建Map集合resultMap
        Map<String,Object> resultMap = new HashMap<String,Object>();
        // 将总的商品数量加入集合
        resultMap.put("totalCount", cart.getTotalCount());
        // 将最后一个添加到购物车的商品名称加入集合
        resultMap.put("lastName",cartItem.getName());
        // 创建GSON对象实例（导入的jar包）
        Gson gson = new Gson();
        // 把 map 集合转换成为 json 字符串
        String json = gson.toJson(resultMap);

        // 返回 json 字符串到 jsp 页面
        response.getWriter().write(json);
    }

    /**
     * 删除商品项
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);

        // 获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 判断购物车对象是否为空
        if(cart != null){
            // 删除购物车商品项
            cart.deleteItem(id);
            // 重定向回原来购物车展示页面
            // getHeader("Referer")：获取请求头中 Referer 参数的值
            // Referer参数：请求发起时，浏览器地址栏中的地址，即可以用其跳回原地址
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    /**
     * 清空购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 判断购物车对象是否为空
        if(cart != null){
            // 清空购物车
            cart.clear();
            // 重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    /**
     * 修改购物车商品数量
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数：商品编号、商品数量
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        int count = WebUtils.parseInt(request.getParameter("count"), 0);

        // 获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 判断购物车对象是否为空
        if(cart != null){
            // 修改购物车商品数量
            cart.updateCount(id,count);
            // 重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

}

























