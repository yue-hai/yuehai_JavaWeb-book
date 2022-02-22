package com.yuehai.test;

import com.yuehai.pojo.User;
import com.yuehai.service.UserService;
import com.yuehai.service.impl.UserServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 月海
 * @create 2021/12/28 20:00
 */
public class UserServiceImplTest {

    UserService userService = new UserServiceImpl();

    @Test
    public void registUser() {
        userService.registUser(new User(null,"yan","qwe","2@qq.com"));
    }

    @Test
    public void login() {
        userService.login(new User(null,"yuehai","000123",null));
    }

    @Test
    public void existsUsername() {
        if(userService.existsUsername("yan") ){
            System.out.println("用户名已存在！");
        }else {
            System.out.println("用户名可用");
        }
    }
}