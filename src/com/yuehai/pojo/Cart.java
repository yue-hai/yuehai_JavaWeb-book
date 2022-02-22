package com.yuehai.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车对象
 * @author 月海
 * @create 2022/1/13 19:41
 */
public class Cart {
    // 总商品数量，不需要，被下面 get 方法替换了
    // private Integer totalCount;
    // 总商品金额，不需要，被下面 get 方法替换了
    // private BigDecimal totalPrice;
    /**
     * 购物车商品
     * key 是商品编号
     * value，是商品信息
     */
    private Map<Integer,CartItem> items = new LinkedHashMap<>();

    /**
     * 添加商品项
     * @param cartItem
     */
    public void addItem(CartItem cartItem){
        // 先查看购物车中是否已经添加过此商品，如果已添加，
        // 则数量累加，总金额更新，如果没有添加过，直接放到集合中即可
        CartItem item = items.get(cartItem.getId());

        if(item == null){
            // 之前没有添加过此商品
            items.put(cartItem.getId(),cartItem);
        }else {
            // 已经 添加过的情况
            // 购物查中的商品数量 + 1
            item.setCount(item.getCount() + 1);
            // 更新总金额
            // multiply()：前面的 x 后面的
            // 即：item.getPrice() x item.getCount()，商品单价 x 商品数量
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }

    /**
     * 删除商品项
     * @param id
     */
    public void deleteItem(Integer id){
        items.remove(id);
    }

    /**
     * 清空购物车
     */
    public void clear(){
        items.clear();
    }

    /**
     * 修改商品数量
     * @param id
     * @param count
     */
    public void updateCount(Integer id,Integer count){
        // 先查看购物车中是否有此商品。如果有，修改商品数量，更新总金额
        CartItem cartItem = items.get(id);
        if(cartItem != null){
            // 修改商品数量
            cartItem.setCount(count);
            // 更新总金额
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }
    }

    // 总的商品数量
    public Integer getTotalCount() {
        // 给 totalCount 一个初始值
        Integer totalCount = 0;
        // 遍历 items 集合
        for(Map.Entry<Integer,CartItem> entry : items.entrySet()){
            // 将遍历的集合中得到的商品数量相加
            totalCount += entry.getValue().getCount();
        }
        // 返回总的商品数量
        return totalCount;
    }
    // public void setTotalCount(Integer totalCount) { this.totalCount = totalCount; }

    // 商品总价
    public BigDecimal getTotalPrice() {
        // 给 getTotalPrice 一个初始值
        BigDecimal totalPrice = new BigDecimal(0);
        // 遍历 items 集合
        for(Map.Entry<Integer,CartItem> entry : items.entrySet()){
            // 将遍历的集合中得到的每个商品的总金额相加，得到所有商品的总金额
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        // 返回所有商品的总金额
        return totalPrice;
    }
    // public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public Map<Integer, CartItem> getItems() { return items; }
    public void setItems(Map<Integer, CartItem> items) { this.items = items; }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
