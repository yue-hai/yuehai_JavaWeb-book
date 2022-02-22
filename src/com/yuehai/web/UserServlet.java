package com.yuehai.web;

import com.google.gson.Gson;
import com.yuehai.pojo.User;
import com.yuehai.service.UserService;
import com.yuehai.service.impl.UserServiceImpl;
import com.yuehai.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 月海
 * @create 2022/1/7 20:53
 */

// 登录注册
public class UserServlet extends BaseServlet {

    // 调用 UserService 中的方法处理业务
    private UserService userService = new UserServiceImpl();

    /**
     * 处理登录请求
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2、调用 userService.login() 处理登录业务
        // 如果返回 null，说明登录失败，返回有值，是登录成功
        User login = userService.login(new User(null, username, password, null));
        if (login == null) {
            // 登录失败，打印用户输入的错误的账号密码
            System.out.println("登录失败，用户名：" + username + "，密码：" + password);

            // 把错误信息和回显的表单信息，保存到 Request 域中
            request.setAttribute("msg", "用户名或密码错误");
            request.setAttribute("username", username);

            // 跳回登录页面
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
        } else {
            // 登录成功
            System.out.println("登录成功，用户名：" + username + "，密码：" + password);

            // 保存用户登录的信息到 session 域中
            request.getSession().setAttribute("user",login);

            // 跳到登录成功页面
            response.sendRedirect( request.getContextPath() + "/pages/user/login_success.jsp");
        }
    }

    /**
     * 处理注册请求
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // 获取 Session 中的验证码：KAPTCHA_SESSION_KEY
        String token = (String) request.getSession().getAttribute("KAPTCHA_SESSION_KEY");
        // 删除 Session 中的验证码（验证码用一次就要换）
        request.getSession().removeAttribute("KAPTCHA_SESSION_KEY");
        // 获取用户输入的验证码
        String code = request.getParameter("code");

        // 调用自己封装在 WebUtils 类中的 copyParamToBean 方法，自动注入参数
        // 传入 request 域中的 Map 集合和 user 对象
        // 返回注入完参数的 user 对象
        User user = WebUtils.copyParamToBean(request.getParameterMap(), new User());

        // 2、检查验证码是否正确
        // equalsIgnoreCase：忽略大小写判断
        if (token != null && token.equalsIgnoreCase(code)) {
            // 正确

            // 3、检查用户名是否可用
            // 返回 true 表示用户名已存在，返回 false 表示用户名可用
            if (userService.existsUsername(username)) {
                // 用户名已存在，不可用
                System.out.println("用户名：" + username + "，已存在，不可用");

                // 把错误信息和回显的表单信息，保存到 Request 域中
                request.setAttribute("msg", "用户名已存在！！");
                request.setAttribute("username", username);
                request.setAttribute("email", email);

                // 跳回注册页面
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
            } else {
                // 用户名可用
                // 调用 service 保存到数据库
                userService.registUser(new User(null, username, password, email));

                // 将用户名放到其对象中，以便 session 获取
                User sessionUser = new User(null, username, password, null);
                // 保存用户登录的信息到 session 域中
                request.getSession().setAttribute("user",sessionUser);

                // 跳转到注册成功页面
                response.sendRedirect( request.getContextPath() + "/pages/user/regist_success.jsp");
            }

        } else {
            // 不正确
            System.out.println("输入的验证码：" + code + "，错误：");

            // 把错误信息和回显的表单信息，保存到 Request 域中
            request.setAttribute("msg", "验证码错误！！");
            request.setAttribute("username", username);
            request.setAttribute("email", email);

            // 跳回注册页面
            request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
        }
    }

    /**
     * 注销
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、销毁 Session 中用户登录的信息（或者销毁 Session）
        request.getSession().invalidate();
        // 2、重定向到首页（或登录页面）。
        response.sendRedirect(request.getContextPath());
    }

    /**
     * 使用 AJAX 提示用户名是否可用
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxExistsUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数 username
        String username = request.getParameter("username");

        // 调用 userService.existsUsername();
        // 返回 true 表示用户名已存在，返回 false 表示用户名可用
        boolean existsUsername = userService.existsUsername(username);

        // 把返回的结果封装成为 map 对象
        Map<String,Object> resultMap = new HashMap<>();
        // 将existsUsername作为key，existsUsername的值作为value，放入map集合中
        resultMap.put("existsUsername",existsUsername);

        // 创建GSON对象实例（导入的jar包）
        Gson gson = new Gson();
        // 把 map 集合转换成为 json 字符串
        String json = gson.toJson(resultMap);

        // 返回 json 字符串到 jsp 页面
        response.getWriter().write(json);
    }

    // 不用再写 doPost 方法，因为继承的父类 BaseServlet 中已有实现方法
}

