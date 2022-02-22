<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑图书</title>

    <%-- 静态包含，base 标签、引入的 css 和 jquery 文件 --%>
    <%@ include file="/pages/common/head.jsp" %>

    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }

        h1 a {
            color: red;
        }

        input {
            text-align: center;
        }
    </style>
</head>
<body>
<div id="header">
    <img class="logo_img" alt="" src="../../static/img/logo.gif">
    <span class="wel_word">编辑图书</span>

    <%-- 静态包含 manager管理模块的菜单  --%>
    <%@include file="/pages/common/manager_menu.jsp" %>

</div>

<div id="main">
    <form action="manager/bookServlet" method="post">
        <%-- 隐藏域，将 book_manager.jsp 页面传递过来的参数 pageNo 传递入 Servlet --%>
        <%-- 添加图书传递的总页码的名称为：pageNo，而不是 pageTotal，是为了方便 --%>
        <input type="hidden" name="pageNo" value="${param.pageNo}">
        <%-- 隐藏域，表明该表单的 请求是什么 --%>
        <%-- 且因为该页面既处理添加请求。也处理修改请求，所以要分清想要进行的操作是什么 --%>
        <%-- 若 Request 域中有 book 对象，则该表单的请求为添加；若没有，则该表单的请求为修改 --%>
        <%-- 因为添加是直接跳转到这个 jps 页面，修改是通过 Servlet --%>
        <input type="hidden" name="action" value="${ empty book ? "add" : "update"}" />
        <%-- 隐藏域，传递 id 到 Servlet 程序 --%>
        <input type="hidden" name="id" value="${book.id}" />

        <table>
            <tr>
                <td>名称</td>
                <td>价格</td>
                <td>作者</td>
                <td>销量</td>
                <td>库存</td>
                <td colspan="2">操作</td>
            </tr>
            <tr>
                <td><input name="name" type="text" value="${book.name}"/></td>
                <td><input name="price" type="text" value="${book.price}"/></td>
                <td><input name="author" type="text" value="${book.author}"/></td>
                <td><input name="sales" type="text" value="${book.sales}"/></td>
                <td><input name="stock" type="text" value="${book.stock}"/></td>
                <td><input type="submit" value="提交"/></td>
            </tr>
        </table>
    </form>

</div>

<%-- 静态包含，页尾 --%>
<%@include file="/pages/common/footer.jsp" %>

</body>
</html>