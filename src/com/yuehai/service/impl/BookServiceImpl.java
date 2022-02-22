package com.yuehai.service.impl;

import com.yuehai.dao.BookDao;
import com.yuehai.dao.impl.BookDaoImpl;
import com.yuehai.pojo.Book;
import com.yuehai.pojo.Page;
import com.yuehai.service.BookService;
import org.apache.commons.beanutils.BeanUtils;

import java.util.List;

/**
 * @author 月海
 * @create 2022/1/8 20:45
 */
public class BookServiceImpl extends BeanUtils implements BookService {
    // 对数据库的操作依赖于 BookDao
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        Page<Book> page = new Page<Book>();

        // 设置每页显示的数量
        page.setPageSize(pageSize);

        // 求总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCount();
        // 设置总记录数
        page.setPageTotalCount(pageTotalCount);

        // 求总页码数：总记录数 / 每页显示的数量
        Integer pageTotal = pageTotalCount / pageSize;
        // 判断，如果除不尽，则总页码数 + 1
        if(pageTotalCount % pageSize > 0){
            pageTotal = pageTotal + 1;
        }
        // 设置总页码数
        page.setPageTotal(pageTotal);

        // 设置当前页码
        page.setPageNo(pageNo);

        // 当前页数据开始的索引值（默认0），即分页查询时开始的记录是第几条
        // （当前页码 - 1) * 每页显示的数量
        int begin = (page.getPageNo() - 1) * pageSize;
        // 求当前页数据：参数1：当前页数据开始的索引值；参数2：每页显示的数量
        List<Book> items = bookDao.queryForPageItems(begin,pageSize);
        // 设置当前页数据
        page.setItems(items);

        return page;
    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {
        Page<Book> page = new Page<Book>();

        // 设置每页显示的数量
        page.setPageSize(pageSize);

        // 求总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCountByPrice(min,max);
        // 设置总记录数
        page.setPageTotalCount(pageTotalCount);

        // 求总页码数：总记录数 / 每页显示的数量
        Integer pageTotal = pageTotalCount / pageSize;
        // 判断，如果除不尽，则总页码数 + 1
        if(pageTotalCount % pageSize > 0){
            pageTotal = pageTotal + 1;
        }
        // 设置总页码数
        page.setPageTotal(pageTotal);

        // 设置当前页码
        page.setPageNo(pageNo);

        // 当前页数据开始的索引值（默认0），即分页查询时开始的记录是第几条
        // （当前页码 - 1) * 每页显示的数量
        int begin = (page.getPageNo() - 1) * pageSize;
        // 求当前页数据：参数1：当前页数据开始的索引值；参数2：每页显示的数量
        List<Book> items = bookDao.queryForPageItemsByPrice(begin,pageSize,min,max);
        // 设置当前页数据
        page.setItems(items);

        return page;
    }
}
