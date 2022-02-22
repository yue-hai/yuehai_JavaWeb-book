<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>尚硅谷会员登录页面</title>

    <%-- 静态包含，base 标签、引入的 css 和 jquery 文件 --%>
    <%@ include file="/pages/common/head.jsp"%>

</head>
<body>
<div id="login_header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
</div>

<div class="login_banner">

    <div id="l_content">
        <span class="login_word">欢迎登录</span>
    </div>

    <div id="content">
        <div class="login_form">
            <div class="login_box">
                <div class="tit">
                    <h1>尚硅谷会员</h1>
                    <a href="pages/user/regist.jsp">立即注册</a>
                </div>
                <div class="msg_cont">
                    <b></b>
                    <%-- 判断 Request 域中的 msg 有没有数据，
                    没有的话显示：请输入用户名和密码；有显示 msg 的值 --%>
                    <span class="errorMsg">${ empty msg ? "请输入用户名和密码" : msg}</span>
                </div>
                <div class="form">
                    <form action="userServlet" method="post">
                        <%-- 隐藏域，表明该表单为 登录请求 --%>
                        <input type="hidden" name="action" value="login" />
                        <label>用户名称：</label>
                        <%-- 判断 Request 域中的 username 有没有数据，
                        没有的话不显示任何值；有显示 username 的值 --%>
                        <input class="itxt" type="text" placeholder="请输入用户名"
                               autocomplete="off" tabindex="1" name="username"
                               value="${ username }" />
                        <br/>
                        <br/>
                        <label>用户密码：</label>
                        <input class="itxt" type="password" placeholder="请输入密码"
                               autocomplete="off" tabindex="1" name="password"/>
                        <br/>
                        <br/>
                        <input type="submit" value="登录" id="sub_btn"/>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>

<%-- 静态包含，页尾 --%>
<%@include file="/pages/common/footer.jsp"%>

</body>
</html>