<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书管理</title>

    <%-- 静态包含，base 标签、引入的 css 和 jquery 文件 --%>
    <%@ include file="/pages/common/head.jsp"%>

    <script type="text/javascript">
        $(function () {
            // 给删除的 a 标签绑定单击事件，用于删除的确认提示操作
            $("a.deleteClass").click(function () {
                /**
                 * confirm 是确认提示框函数
                 * 参数是它的提示内容
                 * 它有两个按钮，一个确认，一个是取消。
                 * 返回 true 表示点击了，确认，返回 false 表示点击取消。
                 */
                /**
                 * 在事件的 function 函数中，有一个 this 对象。
                 * 这个 this 对象，是当前正在响应事件的 dom 对象。
                 * find()：方法获得当前元素集合中每个元素的后代
                 *
                 * $(this).parent().parent().find("td:first").text()：
                 * this（<a>）的 父标签的（<td>） 父标签中（<tr>）
                 * 第一个（first） td 标签的 text 内容（标签中的数据：book.name）
                 */
                return confirm("你确定要删除【" + $(this).parent().parent().find("td:first").text() + "】?");
                // return false// 阻止元素的默认行为===不提交请求
            });
        });


    </script>

</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="../../static/img/logo.gif">
    <span class="wel_word">图书管理系统</span>

    <%-- 静态包含 manager管理模块的菜单  --%>
    <%@include file="/pages/common/manager_menu.jsp"%>

</div>

<div id="main">
    <table>
        <tr>
            <td>名称</td>
            <td>价格</td>
            <td>作者</td>
            <td>销量</td>
            <td>库存</td>
            <td colspan="2">操作</td>
        </tr>

        <%-- 遍历输出 --%>
        <c:forEach items="${page.items}" var="book">
            <tr>
                <td>${book.name}</td>
                <td>${book.price}</td>
                <td>${book.author}</td>
                <td>${book.sales}</td>
                <td>${book.stock}</td>
                <%-- 请求地址为 bookServlet 的 getBook 方法，传递参数为本次循环的图书 id --%>
                <td><a href="manager/bookServlet?action=getBook&id=${book.id}&pageNo=${page.pageNo}">修改</a></td>
                <%-- 请求地址为 bookServlet 的 delete 方法，传递参数为本次循环的图书 id --%>
                <%-- 因为修改图书后还要跳转到这一页，所以传递过去当前页码 --%>
                <td><a class="deleteClass" href="manager/bookServlet?action=delete&id=${book.id}&pageNo=${page.pageNo}">删除</a></td>
            </tr>
        </c:forEach>

        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <%-- 因为添加图书是从最后开始添加，要跳转到最后面的一页，所以传递过去总页码 --%>
            <%-- 添加图书传递的总页码的名称为：pageNo，而不是 pageTotal，是为了方便 --%>
            <td><a href="pages/manager/book_edit.jsp?pageNo=${requestScope.page.pageTotal}">添加图书</a></td>
        </tr>
    </table>

    <%-- 静态包含，页码菜单 --%>
    <%@include file="/pages/common/page.jsp"%>

</div>

<%-- 静态包含，页尾 --%>
<%@include file="/pages/common/footer.jsp"%>

</body>
</html>