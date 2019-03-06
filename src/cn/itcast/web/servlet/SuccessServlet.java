package cn.itcast.web.servlet;

import cn.itcast.domain.User;
import cn.itcast.web.utils.DownLoadUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/successServlet")
public class SuccessServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取request域中共享的user对象
        User user = (User) request.getAttribute("user2");//为了输出结果中表示相对应的用户名
        //添加if判断防止空指针异常
        if(user != null){
            //设置响应的消息体的数据格式以及编码
            response.setContentType("text/html;charset=utf-8");

            //创建ServletContext对象
            //ServletContext域对象范围为整个项目
            ServletContext servletContext = this.getServletContext();
            // 从ServletContext中获得计数器对象
            Integer count = (Integer)servletContext.getAttribute("counter");
            //输出语句到页面（只要服务器未关闭，访问累计数就不会清零）
            response.getWriter().write("欢迎您! " + user.getUsername() + "您是第" + count + "位访问该网站的用户");

            //超链接到下载文件
            response.getWriter().write("<br>");
            response.getWriter().println("<a href='/day14_regist/download_cookie.jsp'>点击下载</a>");
            response.getWriter().println("<a href='/day14_regist/exit.jsp'>点击退出</a>");




        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
