package cn.itcast.dao;

import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

//操作数据库中User表的类
public class UserDao {

    //声明JDBCTemplate对象共用（给一些方法共用）
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    //注册方法
    public int regist(User regist){
        //1.编写sql（此处USER在数据库中大小写都可以）
        String sql = "insert into USER values(null,?,?,?,?,?,?)";
        //调用queryForObject方法封装为一个user对象
        /*User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
                regist.getUsername(), regist.getPassword(), regist.getName(),
                regist.getTel(), regist.getEmail(), regist.getProvince());*/

        int update = template.update(sql, regist.getUsername(), regist.getPassword(), regist.getName(),
                regist.getTel(), regist.getEmail(), regist.getProvince());
        return update;
    }

}
