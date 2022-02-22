<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>

    <%-- 静态包含，base 标签、引入的 css 和 jquery 文件 --%>
    <%@ include file="/pages/common/head.jsp" %>

    <script type="text/javascript">
        $(function () {
            // 给删除按钮绑定确认提示事件
            $("a.deleteItem").click(function () {
                return confirm("你确定要删除【" + $(this).parent().parent().find("td:first").text() +"】吗?");
            });

            // 给清空购物车按钮绑定确认提示事件
            $("#clearCart").click(function () {
                return confirm("你确定要清空购物车吗?");
            });

            // 给商品数量输入框绑定 onchange 内容发生改变事
            $("input.updateCount").change(function () {
                // 获取商品名称
                var name = $(this).parent().parent().find("td:first").text();
                // 获取该标签内的 bookId 属性的值
                var id = $(this).attr("bookId");
                // 获取商品数量
                var count = $(this).val();

                // confirm() 方法会根据用户点击返回一个 Boolean 类型的参数
                if(confirm("你确定要将【" + name + "】商品修改数量为：" + count + " 吗?")){
                    //发起请求，给服务器保存修改
                    location.href = "${pageScope.basePath}cartServlet?action=updateCount&id="+id+"&count="+count;
                }else{
                    // defaultValue 属性是表单项 Dom 对象的属性。它表示默认的 value 属性值
                    this.value = this.defaultValue;
                }
            });

        });
    </script>

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
            <a href="index.jsp">返回商城</a>
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

    <table>

        <%-- 如果购物车为空的情况 --%>
        <c:if test="${empty sessionScope.cart.items}">
            <tr>
                <td colspan="5"><a href="index.jsp">当前购物车为空！</a></td>
            </tr>
        </c:if>
        <%-- 如果购物车不为空的情况 --%>
        <c:if test="${not empty sessionScope.cart.items}">
            <tr>
                <td>商品名称</td>
                <td>数量</td>
                <td>单价</td>
                <td>金额</td>
                <td>操作</td>
            </tr>
            <%-- 循环遍历 session 域中的 cart 数据 --%>
            <c:forEach items="${sessionScope.cart.items}" var="entry">
                <tr>
                        <%-- entry：循环语句的var的值 --%>
                        <%-- entry.value.name：获取当前遍历的集合的key为name的值 --%>
                    <td>${entry.value.name}</td>
                    <td>
                        <input class="updateCount" type="text" style="width: 80px;" bookId="${entry.value.id}" value="${entry.value.count}" >
                    </td>
                    <td>${entry.value.price}</td>
                    <td>${entry.value.totalPrice}</td>
                    <td><a class="deleteItem" href="cartServlet?action=deleteItem&id=${entry.value.id}">删除</a></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>

    <%-- 购物车不为空才会输出的页面 --%>
    <c:if test="${not empty sessionScope.cart.items}">
        <div class="cart_info">
            <span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
            <span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
            <span class="cart_span"><a id="clearCart" href="cartServlet?action=clear">清空购物车</a></span>
            <span class="cart_span"><a href="orderServlet?action=createOrder">去结账</a></span>
        </div>
    </c:if>

</div>

<%-- 静态包含，页尾 --%>
<%@include file="/pages/common/footer.jsp" %>

</body>
</html>