package com.yuehai.pojo;

import java.math.BigDecimal;

/**
 * 购物车的商品项
 * @author 月海
 * @create 2022/1/13 19:33
 */
public class CartItem {
    // 商品编号
    private Integer id;
    // 商品名称
    private String name;
    // 商品数量
    private Integer count;
    // 商品单价
    private BigDecimal price;
    // 商品总价
    private BigDecimal totalPrice;

    public CartItem() { }
    public CartItem(Integer id, String name, Integer count, BigDecimal price, BigDecimal totalPrice) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
