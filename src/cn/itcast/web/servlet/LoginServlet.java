package cn.itcast.web.servlet;

import cn.itcast.dao.UserDao2;
import cn.itcast.domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*使用Request对象获取请求消息*/
        //1.设置编码
        req.setCharacterEncoding("utf-8");
        //2.1获取所有请求参数
        Map<String, String[]> map = req.getParameterMap();
        //2.2获取验证码的请求参数
        String checkCode = req.getParameter("checkCode");
        //3.1创建User对象
        User loginUser  = new User();
        //3.2使用BeanUtils封装
        try {
            BeanUtils.populate(loginUser,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //4.调用UserDao的login方法
        UserDao2 dao2 = new UserDao2();
        User user2=null;
        try{
            user2 = dao2.login(loginUser);
        }catch (Exception e){
            user2=null;
        }
        //获取存储生成的验证码数据的HttpSession对象
        HttpSession session = req.getSession();
        //从session中取值（生成的验证码数据），强转为字符串，以便使用equalsIgnoreCase()方法
        String checkCode_session = (String)session.getAttribute("checkCode_session");
        //先判断验证码是否正确
        if(checkCode_session.equalsIgnoreCase(checkCode)){
            //忽略大小写比较字符串
            //验证码一致
            //5.判断user
            if(user2 == null){
                //转发到登录失败页面
                //存储提示信息到request
                req.setAttribute("login_error","登录失败，用户名或密码错误");
                /*此处使用转发而不用重定向，因为跳转回登录页面，地址没变，
                而且存储数据并且需要能够取到数据，所以需要使用request域对象共享*/
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }else{
                //转发到登录成功页面
                //存储数据
                req.setAttribute("user2",user2);//括号中为键和值
                //创建ServletContext对象
                //使用ServletContext域对象可进行所有用户的所有请求数据，符合访问网站的累计数需求
                ServletContext servletContext = this.getServletContext();
                // 从ServletContext中获得计数器对象
                Integer count = (Integer)servletContext.getAttribute("counter");
                // 如果为空，则在ServletContext中设置一个计数器的属性.即在第一次提交请求时创建该属性
                if(count == null){
                    count = 1;
                    servletContext.setAttribute("counter", count);
                }else{
                    //之后的请求，每次对计数器值加1
                    servletContext.setAttribute("counter", count+1);
                }
                //转发
                req.getRequestDispatcher("/successServlet").forward(req,resp);
            }
        }else{
            //验证码不一致
            //存储提示信息到request
            req.setAttribute("cc_error","验证码输入错误");
            //转发到登录页面
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
