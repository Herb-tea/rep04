<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.net.URLDecoder" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/1/7
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

        <b>请进行下载：</b><br>


        <a href="/day14_regist/downloadServlet?filename=小九.jpg">图片1</a>

        <a href="/day14_regist/downloadServlet?filename=1.avi">视频</a>

        <%
        //1.获取所有Cookie
        Cookie[] cookies = request.getCookies();
        /*由于初始时没有cookie为lastTime(没有关于此功能数据)，
        所以需要定义个flag对象引导初始化创建符合此功能的Cookie对象*/
        boolean flag = false;//
        //2.遍历cookie数组
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                //3.获取cookie的名称
                String name = cookie.getName();
                //4.判断名称是否是：lastTime
                if("lastTime".equals(name)){
                //有该Cookie，不是第一次访问
                flag = true;//有lastTime的cookie
                //响应数据
                //获取Cookie的value，时间
                String value = cookie.getValue();
                //URL解码：
                value = URLDecoder.decode(value,"utf-8");
                response.getWriter().write("<h1>欢迎回来，您上次访问时间为:"+value+"</h1>");

                //设置Cookie的当前的value
                //获取当前时间的字符串，重新设置Cookie的值，重新发送cookie
                Date date  = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                String str_date = sdf.format(date);
                //URL编码
                str_date = URLEncoder.encode(str_date,"utf-8");
                //将当前时间的数据绑定cookie中，为下一次访问作为上一次时间做准备
                cookie.setValue(str_date);
                //发送绑定相对于下一次而言是上一次访问时间的当前时间数据的响应给浏览器
                response.addCookie(cookie);
                //设置cookie的存活时间
                cookie.setMaxAge(60 * 60 * 24 * 30);//一个月
                break;
                }
            }
        }

        if(cookies == null || cookies.length == 0 || flag == false){
        //没有，第一次访问
        //设置Cookie的value
        //获取当前时间的字符串，设置Cookie的值，重新发送cookie
        Date date  = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String str_date = sdf.format(date);
        //URL编码
        str_date = URLEncoder.encode(str_date,"utf-8");
        //创建Cookie对象
        Cookie cookie = new Cookie("lastTime",str_date);
        //携带数据发送Cookie对象
        response.addCookie(cookie);
        //设置cookie的存活时间
        cookie.setMaxAge(60 * 60 * 24 * 30);//一个月
        response.getWriter().write("<h1>您好，欢迎您首次访问</h1>");
        }
        %>
</body>
</html>
