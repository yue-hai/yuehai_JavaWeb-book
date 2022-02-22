<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/5
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- 静态包含 manager管理模块的菜单  --%>
<div>
    <%-- manager/bookServlet?action=list：bookServlet 下的 page 方法 --%>
    <%-- 然后从 page 方法中处理完业务后再跳到 book_manager.jsp --%>
    <a href="manager/bookServlet?action=page&pageNo=1">图书管理</a>
    <a href="pages/manager/order_manager.jsp">订单管理</a>
    <a href="index.jsp">返回商城</a>
</div>