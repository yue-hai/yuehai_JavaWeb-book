package com.yuehai.service.impl;

import com.yuehai.dao.UserDao;
import com.yuehai.dao.impl.UserDaoImpl;
import com.yuehai.pojo.User;
import com.yuehai.service.UserService;

/**
 * @author 月海
 * @create 2021/12/28 19:42
 */
public class UserServiceImpl implements UserService {
    // 对数据库的操作依赖于 UserDao
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {
        // 查询是否存在此用户名
        if(userDao.queryUserByUsername(username) == null){
            // 返回 null 则表示不存在，返回 false ，可注册
            return false;
        }
        return true;
    }
}
