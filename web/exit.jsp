<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/1/7
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        //销毁session
        session.invalidate();
        //重定向到登录页面
        response.sendRedirect("/day14_regist/login.jsp");
    %>
</body>
</html>
