package com.yuehai.dao;

import com.yuehai.pojo.Book;

import java.util.List;

/**
 * @author 月海
 * @create 2022/1/8 19:55
 */
public interface BookDao {
    // 添加
    public int addBook(Book book);
    // 删除
    public int deleteBookById(Integer id);
    // 修改
    public int updateBook(Book book);
    // 根据 id 查询一个书
    public Book queryBookById(Integer id);
    // 查询全部书籍
    public List<Book> queryBooks();
    // 分页：求总记录数
    Integer queryForPageTotalCount();
    // 分页：求当前页数据
    List<Book> queryForPageItems(int begin, int pageSize);
    // 依据价格搜索：求总记录数
    Integer queryForPageTotalCountByPrice(int min, int max);
    // 依据价格搜索：求当前页数据
    List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);
}
