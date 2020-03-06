<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        登录注册页面
    </title>
</head>
<body>
<h2>欢迎使用嗖嗖业务移动大厅!</h2>
<div>
    <form id="zc" action="${pageContext.request.contextPath}/user/login" method="post">
        号码：<input type="text" required id ="number" name="number"><br>
        密码：<input type="password" required id="pwd" name="pwd"><br>
        <input type="submit" value="登录">
        <input type="button" value="注册" onclick="location.href='${pageContext.request.contextPath}/user/register'">
    </form>
</div>
</body>
<script>
    if('${status}'!=''){
        if('${status}'==0){
            alert('登录成功，即将跳转至用户详情页！')
            location.href='${pageContext.request.contextPath}/user/userInfo'
        }else if('${status}'==1){
            alert('该用户不存在！');
        }
        else if ('${status}'==2){
            alert('密码错误')
        }
    }
</script>
</html>
