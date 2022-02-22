package com.yuehai.service;

import com.yuehai.pojo.Cart;

/**
 * @author 月海
 * @create 2022/1/14 17:32
 */
public interface OrderService {
    // 生成订单
    public String createOrder(Cart cart, Integer userId);
}
