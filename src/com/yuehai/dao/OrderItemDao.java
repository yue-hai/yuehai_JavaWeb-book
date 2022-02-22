package com.yuehai.dao;

import com.yuehai.pojo.OrderItem;

/**
 * @author 月海
 * @create 2022/1/14 17:17
 */
public interface OrderItemDao {
    // 保存订单项
    public int saveOrderItem(OrderItem orderItem);
}
