<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
</head>
<body>
<div>
    <form id="addUser" action="${pageContext.request.contextPath}/user/register" method="post">
        用户名：<input type="text" required id="id" name="id"><br>
        密码：<input type="password" required id="pwd" name="pwd"><br>
        号码： <input type="text" required id="number" name="number"><br>
        预存话费：<input type="text" required id="money" name="money"><br>
        套餐类型：<input type="text" required id="package" name="package"><br>
        <input type="submit" value="注册">
        <input type="reset" value="重置"/>
    </form>
</div>
</body>
<script>
    if ('${status}' != '') {
        if ('${status}' == 0) {
            alert('注册成功，点击确定跳转到登录页面！')
            location.href = '${pageContext.request.contextPath}/user/login'
        }
        if ('${status}' == 1) {
            alert('exception，注册失败！')
        }
        if ('${status}' == 2) {
            alert('用户信息已存在！')
        }
    }
</script>
</html>
