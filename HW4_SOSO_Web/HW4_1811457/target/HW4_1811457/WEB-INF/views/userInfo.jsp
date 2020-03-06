<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人中心</title>
</head>
<body>
<div>
    <c:if test="${not empty user}">
        <div>欢迎您，${user.id}${user.number}<br>
            账户余额：${user.consume}<br>
            通话时长：${user.talktime}<br>
            短信：${user.send}<br>
            流量:${user.flow}<br>
            创建时间:${user.createtime}<br>
            <a href="${pageContext.request.contextPath}/user/logout">注销</a></div></div>
    </c:if>
    <c:if test="${ empty user}">
        对不起，请先<a href="${pageContext.request.contextPath}/user/login">登录</a>
    </c:if>
</div>
</body>
</html>
