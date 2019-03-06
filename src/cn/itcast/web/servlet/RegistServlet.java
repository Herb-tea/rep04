package cn.itcast.web.servlet;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import org.apache.commons.beanutils.BeanUtils;


import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registServlet")
public class RegistServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*使用Request对象获取请求消息*/
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //2.获取所有请求参数
        Map<String, String[]> map = request.getParameterMap();
        //3.1创建User对象
        User registUser = new User();
        try {
            //3.2参数个数较多,使用BeanUtils封装registUser对象
            BeanUtils.populate(registUser,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //4.调用UserDao的registUser方法
        UserDao dao1 = new UserDao();
        //注册重复的用户名，则会与唯一约束产生冲突，进行捕获异常
        int regist;
        try{ regist = dao1.regist(registUser);
        }catch (Exception e){
            regist=0;
        }
        //5.判断regist
        if(regist==1){
            //存储数据
            request.setAttribute("user",regist);
            /*重定向（需要加工程名）
            response.sendRedirect("/day14_regist/login.jsp");*/
            //动态获取虚拟目录
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }else{
            //转发到注册失败页面，地址已变，使用重定向
            //request.getRequestDispatcher("/failServlet").forward(request,response);
            response.sendRedirect(request.getContextPath()+"/failServlet");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
