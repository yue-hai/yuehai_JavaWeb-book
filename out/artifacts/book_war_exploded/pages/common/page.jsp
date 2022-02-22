<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ccj77
  Date: 2022/1/9
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- 静态包含 页码菜单  --%>
<%--
    pageNo：当前页码
    pageTotal：总页码
    pageSize：当前页显示数量
    PageTotalCount：总记录数
--%>
<div id="page_nav">
    <c:choose>
        <%-- 判断当前是否是第一页，是的话则 首页 和 上一页 不可用 --%>
        <c:when test="${page.pageNo <= 1}">
            <a>首页</a>
            <a>上一页</a>
        </c:when>
        <%-- 判断当前是否是第一页，不是的话则 首页 和 上一页 可用 --%>
        <c:when test="${page.pageNo > 1}">
            <a href="${page.url}&pageNo=1">首页</a>
            <a href="${page.url}&pageNo=${page.pageNo-1}">上一页</a>
        </c:when>
    </c:choose>

    <%-- 页码输出的开始 --%>
    <c:choose>
        <%-- 情况 1：如果总页码小于等于 5 的情况，页码的范围是：1-总页码 --%>
        <c:when test="${page.pageTotal <= 5}">
            <%-- 设置 begin 和 end 的值，以便循环语句获取 --%>
            <c:set var="begin" value="1"></c:set>
            <c:set var="end" value="${page.pageTotal}"></c:set>
        </c:when>

        <%-- 情况 2：总页码大于 5 的情况。假设一共 10 页 --%>
        <c:when test="${page.pageTotal > 5}">
            <c:choose>
                <%-- 小情况 1：当前页码为前面 3 个：1，2，3 的情况，页码范围是：1-5 --%>
                <c:when test="${page.pageNo <= 3}">
                    <%-- 设置 begin 和 end 的值，以便循环语句获取 --%>
                    <c:set var="begin" value="1"></c:set>
                    <c:set var="end" value="5"></c:set>
                </c:when>

                <%-- 小情况 2：当前页码为最后 3 个，8，9，10，页码范围是：总页码减 4 - 总页码 --%>
                <c:when test="${page.pageNo > page.pageTotal - 3}">
                    <%-- 设置 begin 和 end 的值，以便循环语句获取 --%>
                    <c:set var="begin" value="${page.pageTotal - 4}"></c:set>
                    <c:set var="end" value="${page.pageTotal}"></c:set>
                </c:when>

                <%-- 小情况 3：4，5，6，7，页码范围是：当前页码减 2 - 当前页码加 2 --%>
                <c:otherwise>
                    <%-- 设置 begin 和 end 的值，以便循环语句获取 --%>
                    <c:set var="begin" value="${page.pageNo - 2}"></c:set>
                    <c:set var="end" value="${page.pageNo + 2}"></c:set>

                </c:otherwise>
            </c:choose>
        </c:when>

    </c:choose>

    <%-- 遍历输出 --%>
    <c:forEach begin="${begin}" end="${end}" var="i">
        <%-- 判断是不是当前页码，是就不能点击 --%>
        <c:if test="${page.pageNo == i}">
            【${i}】
        </c:if>
        <%-- 判断是不是当前页码，不是就能点击 --%>
        <c:if test="${page.pageNo != i}">
            <a href="${page.url}&pageNo=${i}">${i}</a>
        </c:if>
    </c:forEach>
    <%-- 页码输出的结束 --%>

    <c:choose>
        <%-- 判断当前是否是最后一页，不是的话则 下一页 和 末页 可用 --%>
        <c:when test="${page.pageNo != page.pageTotal}">
            <a href="${page.url}&pageNo=${page.pageNo+1}">下一页</a>
            <a href="${page.url}&pageNo=${page.pageTotal}">末页</a>
        </c:when>
        <%-- 判断当前是否是最后一页，是的话则 下一页 和 末页 不可用 --%>
        <c:when test="${page.pageNo == page.pageTotal}">
            <a>下一页</a>
            <a>末页</a>
        </c:when>
    </c:choose>

    共${page.pageTotal}页，${page.pageTotalCount}条记录
    到第<input value="${page.pageNo}" name="pn" id="pn_input"/>页
    <input id="searchPageBth" type="button" value="确定">
</div>

<%-- 给确定按钮绑定单击事件 --%>
<script type="text/javascript">
    $(function (){5
        // 跳到指定的页码
        $("#searchPageBth").click(function () {
            // 获取 id 为 pn_input 的标签里的值
            var pageNo = $("#pn_input").val();

            /**
             * javaScript 语言中提供了一个 location 地址栏对象
             * 它有一个属性叫 href.它可以获取浏览器地址栏中的地址
             * href 属性可读，可写
             */
            location.href = "${pageScope.basePath}${page.url}&pageNo=" + pageNo;
        });
    });
</script>