package com.yuehai.pojo;

import java.util.List;

/**
 * @author 月海
 * @create 2022/1/9 19:19
 */

/**
 * Page 是分页的模型对象
 * @param <T> 是具体的模块的 javaBean 类
 */
public class Page<T> {
    // 常量，每页显示的数量
    public static final Integer PAGE_SIZE = 4;

    // 当前页码
    private Integer pageNo;
    // 总页码
    private Integer pageTotal;
    // 当前页显示数量
    private Integer pageSize = PAGE_SIZE;
    // 总记录数
    private Integer PageTotalCount;
    // 当前页的数据
    private List<T> items;
    // 分页条的请求地址
    private String url;

    public Page() { }
    public Page(Integer pageNo, Integer pageTotal, Integer pageSize, Integer pageTotalCount, List<T> items, String url) {
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.pageSize = pageSize;
        PageTotalCount = pageTotalCount;
        this.items = items;
        this.url = url;
    }

    public static Integer getPageSize() { return PAGE_SIZE; }
    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }
    public Integer getPageTotalCount() { return PageTotalCount; }
    public void setPageTotalCount(Integer pageTotalCount) { PageTotalCount = pageTotalCount; }
    public List<T> getItems() { return items; }
    public void setItems(List<T> items) { this.items = items; }
    public Integer getPageNo() { return pageNo; }
    public void setPageNo(Integer pageNo) {
        // 跳页按钮的数据边界的有效性检查
        if(pageNo < 1){
            pageNo = 1;
        }
        if(pageNo > pageTotal){
            pageNo = pageTotal;
        }

        this.pageNo = pageNo;
    }
    public Integer getPageTotal() { return pageTotal; }
    public void setPageTotal(Integer pageTotal) { this.pageTotal = pageTotal; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageTotal=" + pageTotal +
                ", pageSize=" + pageSize +
                ", PageTotalCount=" + PageTotalCount +
                ", items=" + items +
                ", url='" + url + '\'' +
                '}';
    }
}
