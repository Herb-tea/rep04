package cn.itcast.test;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.UserDao2;
import cn.itcast.domain.User;
import org.junit.Test;

public class UserDaoTest {

    @Test
    public void testregist(){
        //创建USer对象
        User registUser = new User();
        //给成员变量赋值
        registUser.setUsername("刘德");
        registUser.setPassword("33");
        registUser.setName("张飞");
        registUser.setTel("1351234567");
        registUser.setEmail("123");
        registUser.setProvince("福建");


        UserDao dao = new UserDao();
        int regist = dao.regist(registUser);
        System.out.println(regist);
    }

    @Test
    public void testLogin(){
        User loginuser = new User();
        loginuser.setUsername("刘德华");
        loginuser.setPassword("333");


        UserDao2 dao2 = new UserDao2();
        User user = dao2.login(loginuser);

        System.out.println(user);
    }
}
