<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>书城首页</title>

    <%-- 静态包含，base 标签、引入的 css 和 jquery 文件 --%>
    <%@ include file="/pages/common/head.jsp" %>

    <Script type="text/javascript">
        $(function () {
            // 给加入购物车绑定单击事件
            $("button.addToCart").click(function () {
                // 在事件响应的 function 函数中，有一个 this 对象
                // 这个 this 对象，是当前正在响应事件的 dom 对象
                // 获取当前标签中的 bookId 属性的值
                var bookId = $(this).attr("bookId");

                /**
                 * 发送 ajax 请求，添加商品到购物车
                 * 请求类型为：GET，返回的数据类型为：JSON
                 * 参数1：url：请求的 url 地址
                 * 参数2：data：发送给服务器的数据（url参数）
                 *      发送的数据为：?action=ajaxExistsUsername&username=用户的输入
                 * 参数3：callback：成功的回调函数
                 */
                $.getJSON("${basePath}cartServlet","action=ajaxAddItem&id=" + bookId,function (data) {
                    $("#cartTotalCount").text("您的购物车中有" + data.totalCount + "件商品")
                    $("#cartLastName").text(data.lastName);
                });
            });
        });
    </Script>

</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">网上书城</span>
    <div>
        <%-- 如果用户还没有登录(sessionScope.user 为空)，显示登录和注册的菜单 --%>
        <c:if test="${empty sessionScope.user}">
            <a href="pages/user/login.jsp">登录</a> |
            <a href="pages/user/regist.jsp">注册</a> &nbsp;&nbsp;
            <a href="pages/cart/cart.jsp">购物车</a>
            <a href="pages/manager/manager.jsp">后台管理</a>
        </c:if>
        <%-- 如果用户已登录(sessionScope.user 不为空)，显示登录成功之后的用户信息 --%>
        <c:if test="${not empty sessionScope.user}">
            <%-- 静态包含，登录成功之后的菜单 --%>
            <%@ include file="/pages/common/login_success_menu.jsp" %>
        </c:if>

    </div>
</div>
<div id="main">
    <div id="book">
        <div class="book_cond">
            <form action="client/clientBookServlet" method="get">
                <%-- 隐藏域，传递：action=pageByPrice --%>
                <input type="hidden" name="action" value="pageByPrice">
                <%-- param.min：获取 url 里参数 min 的值，搜索参数的回显 --%>
                价格：<input id="min" type="text" name="min" value="${param.min}"> 元 -
                <input id="max" type="text" name="max" value="${param.max}"> 元
                <input type="submit" value="查询"/>
            </form>
        </div>

        <div style="text-align: center">
        <%-- 如果购物车为空的情况 --%>
        <c:if test="${empty sessionScope.cart.items}">
            <span id="cartTotalCount"> </span>
            <div>
                <span id="cartLastName" style="color: red">当前购物车为空</span>
            </div>
        </c:if>
        <%-- 如果购物车不为空的情况 --%>
        <c:if test="${not empty sessionScope.cart.items}">
            <span id="cartTotalCount">您的购物车中有${sessionScope.cart.totalCount}件商品</span>
            <div>
                您刚刚将<span id="cartLastName" style="color: red">${sessionScope.lastName}</span>加入到了购物车中
            </div>
        </c:if>
    </div>
        <%-- 循环输出前台商品 --%>
        <c:forEach items="${requestScope.page.items}" var="book">
            <div class="b_list">
                <div class="img_div">
                    <img class="book_img" alt="" src="static/img/default.jpg"/>
                </div>
                <div class="book_info">
                    <div class="book_name">
                        <span class="sp1">书名:</span>
                        <span class="sp2">${book.name}</span>
                    </div>
                    <div class="book_author">
                        <span class="sp1">作者:</span>
                        <span class="sp2">${book.author}</span>
                    </div>
                    <div class="book_price">
                        <span class="sp1">价格:</span>
                        <span class="sp2">￥${book.price}</span>
                    </div>
                    <div class="book_sales">
                        <span class="sp1">销量:</span>
                        <span class="sp2">${book.sales}</span>
                    </div>
                    <div class="book_amount">
                        <span class="sp1">库存:</span>
                        <span class="sp2">${book.stock}</span>
                    </div>
                    <div class="book_add">
                        <button bookId="${book.id}" class="addToCart">加入购物车</button>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>

    <%-- 静态包含，页码菜单 --%>
    <%@include file="/pages/common/page.jsp" %>

</div>

<%-- 静态包含，页尾 --%>
<%@include file="/pages/common/footer.jsp" %>

</body>
</html>