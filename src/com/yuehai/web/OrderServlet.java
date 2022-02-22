package com.yuehai.web;

import com.yuehai.pojo.Cart;
import com.yuehai.pojo.User;
import com.yuehai.service.OrderService;
import com.yuehai.service.impl.OrderServiceImpl;
import com.yuehai.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 月海
 * @create 2022/1/14 19:32
 */
public class OrderServlet extends BaseServlet {
    // Servlet 的实现依赖于 Service
    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 先从 session 中获取 Cart 购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 获取用户名称（账号）
        User user = (User) request.getSession().getAttribute("user");
        // 判断用户是否登录
        if(user == null){
            // 未登录直接跳到登录页面
            request.getRequestDispatcher("pages/user/login.jsp").forward(request,response);
            return;
        }

        // 获取用户 id
        Integer userId = user.getId();

        // 调用 createOrder(Cart,Userid) 生成订单，返回订单号
        // try-catch，使用事务，保证安全
        String orderId = orderService.createOrder(cart, userId);

        // 将订单号保存到 session 域中
        request.getSession().setAttribute("orderId",orderId);

        // 重定向到结账页面
        response.sendRedirect(request.getContextPath() + "/pages/cart/checkout.jsp");

    }
}















