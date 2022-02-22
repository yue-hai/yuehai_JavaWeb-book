package com.yuehai.service;

import com.yuehai.pojo.Book;
import com.yuehai.pojo.Page;

import java.util.List;

/**
 * @author 月海
 * @create 2022/1/8 20:43
 */
public interface BookService {
    // 添加
    public void addBook(Book book);
    // 删除
    public void deleteBookById(Integer id);
    // 修改
    public void updateBook(Book book);
    // 根据 id 查询一个书
    public Book queryBookById(Integer id);
    // 查询书籍
    public List<Book> queryBooks();
    // 分页方法
    Page<Book> page(int pageNo, int pageSize);
    // 依据价格搜索
    Page<Book> pageByPrice(int pageNo, int pageSize,int min,int max);
}
