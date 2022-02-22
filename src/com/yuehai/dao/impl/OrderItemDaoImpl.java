package com.yuehai.dao.impl;

import com.yuehai.dao.BaseDao;
import com.yuehai.dao.OrderItemDao;
import com.yuehai.pojo.OrderItem;

/**
 * @author 月海
 * @create 2022/1/14 17:18
 */
public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item(`name`,`count`,`price`,`total_price`,`order_id`) values(?,?,?,?,?)";
        return update(sql,orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),orderItem.getTotalPrice(),orderItem.getOrderId());
    }
}
