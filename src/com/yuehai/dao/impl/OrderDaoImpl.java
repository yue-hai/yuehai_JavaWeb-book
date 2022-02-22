package com.yuehai.dao.impl;

import com.yuehai.dao.BaseDao;
import com.yuehai.dao.OrderDao;
import com.yuehai.pojo.Order;

/**
 * @author 月海
 * @create 2022/1/14 17:14
 */
public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql = "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) values(?,?,?,?,?)";
        return update(sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }
}
