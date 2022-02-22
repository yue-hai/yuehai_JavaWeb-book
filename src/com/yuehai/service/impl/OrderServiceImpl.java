package com.yuehai.service.impl;

import com.yuehai.dao.BookDao;
import com.yuehai.dao.OrderDao;
import com.yuehai.dao.OrderItemDao;
import com.yuehai.dao.impl.BookDaoImpl;
import com.yuehai.dao.impl.OrderDaoImpl;
import com.yuehai.dao.impl.OrderItemDaoImpl;
import com.yuehai.pojo.*;
import com.yuehai.service.OrderService;

import java.util.Date;
import java.util.Map;


/**
 * @author 月海
 * @create 2022/1/14 17:33
 */
public class OrderServiceImpl implements OrderService {
    // 对数据库的操作依赖于 Dao 层
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        // 订单号，具有唯一性
        // System.currentTimeMillis()：获取当前时间戳
        String orderId = System.currentTimeMillis() + "" + userId;
        // 创建一个订单对象
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);
        // 保存订单
        orderDao.saveOrder(order);

        // 模拟错误
        // int i = 12/0;

        // 遍历购物车中每一个商品项转换成为订单项保存到数据库
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()){
            // 获取每一个购物车中的商品项
            CartItem cartItem = entry.getValue();
            // 转换为每一个订单项
            OrderItem orderItem = new OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(),orderId);
            // 保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

            // 更新销量和库存
            // 根据 id 查询图书的商品信息
            Book book = bookDao.queryBookById(cartItem.getId());
            // 更新销量
            book.setSales(book.getSales() + cartItem.getCount());
            // 更新库存
            book.setStock(book.getStock() - cartItem.getCount());
            // 保存到图书数据库
            bookDao.updateBook(book);
        }
        // 清空购物车
        cart.clear();

        // 返回订单号
        return orderId;
    }
}






















