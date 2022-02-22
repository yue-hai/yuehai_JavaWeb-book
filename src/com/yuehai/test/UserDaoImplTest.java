package com.yuehai.test;

import com.yuehai.dao.UserDao;
import com.yuehai.dao.impl.UserDaoImpl;
import com.yuehai.pojo.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 月海
 * @create 2021/12/28 17:20
 */
public class UserDaoImplTest {

    UserDao userDao = new UserDaoImpl();

    @Test
    public void queryUserByUsername() {
        if(userDao.queryUserByUsername("admin") == null){
            System.out.println("用户名可用");
        }else {
            System.out.println("用户名已存在");
        }
    }

    @Test
    public void saveUser() {
        User user = new User(null,"yu","000123","1@qq.com");
        int saveUser = userDao.saveUser(user);
        System.out.println(saveUser);

    }

    @Test
    public void queryUserByUsernameAndPassword() {
        if(userDao.queryUserByUsernameAndPassword("admin","admin") == null){
            System.out.println("用户名或密码错误");
        }else {
            System.out.println("登录成功");
        }

    }
}