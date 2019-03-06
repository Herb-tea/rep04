<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/1/7
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script>
        window.onload = function () {
            document.getElementById("img").onclick = function () {
                this.src = "/day14_regist/check_Code_Servlet?" + new Date().getTime();
            }
        }
    </script>

    <style>
        div {
            color: red;
        }
    </style>

</head>
<body>
<form action="${pageContext.request.contextPath}/loginServlet" method="post">
    <table>
        <tr>
            <td colspan="2">
                <font color="red">恭喜您，注册成功!请进行登录操作：</font>
            </td>
        </tr>
        <tr>
            <td>用户名</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密　码</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>验证码</td>
            <td><input type="text" name="checkCode"></td>
        </tr>
        <tr>
            <td colspan="2"><img id="img" src="/day14_regist/check_Code_Servlet"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="登录"></td>
        </tr>
    </table>
</form>

<%--获取request提示信息--%>
<%--<div><%=request.getAttribute("cc_error")%></div>
<div><%=request.getAttribute("login_error")%></div>--%>
<%--EL表达式简化代码（域从小到大查找），
此处也省略了判断是否为null（此表达式显示空字符串），
可以解决页面显示null值的问题--%>
<div>${cc_error}</div>
<div>${login_error}</div>
</body>
</html>
