<%--
  Created by IntelliJ IDEA.
  User: ccj77
  Date: 2022/1/7
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- base 标签、引入的 css 和 jquery 文件 --%>

<%
    String basePath = request.getScheme()// 协议名，如 http
            + "://"                      //
            + request.getServerName()    // 服务器 ip 地址
            + ":"                        //
            + request.getServerPort()    // 服务器 端口号
            + request.getContextPath()   // 工程名
            + "/";                       //
%>

<!-- base 标签，永远固定相对路径跳转的结果，从 web 路径开始计算 -->
<%-- 此处的 basePath 为服务器的项目地址，动态的获取，防止因路径问题获取不到资源 --%>
<base href="<%=basePath%>">

<link type="text/css" rel="stylesheet" href="static/css/style.css">
<!-- ../../：父目录的父目录 -->
<script type="text/javascript" src="static/script/jquery-1.7.2.js" ></script>