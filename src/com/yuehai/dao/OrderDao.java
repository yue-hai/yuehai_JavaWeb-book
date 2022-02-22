package com.yuehai.dao;

import com.yuehai.pojo.Order;

/**
 * @author 月海
 * @create 2022/1/14 17:12
 */
public interface OrderDao {
    // 保存订单
    public int saveOrder(Order order);
}
