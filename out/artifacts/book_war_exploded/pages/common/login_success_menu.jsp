<%--
  Created by IntelliJ IDEA.
  User: ccj77
  Date: 2022/1/7
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- 登录成功之后的菜单 --%>
<div>
    <%-- ${sessionScope.user.username}：获取服务器保存到 session 域中的 名为 user 的对象中的 username 的值 --%>
    <span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临尚硅谷书城</span>
    <a href="pages/cart/cart.jsp">购物车</a>
    <a href="pages/order/order.jsp">我的订单</a>
    <a href="userServlet?action=logout">注销</a>
    <a href="index.jsp">返回</a>
</div>