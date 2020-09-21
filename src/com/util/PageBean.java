package com.util;

import java.util.List;

/**
 * @author: wangxiangnan
 * @time: 2020/8/21 8:32
 * @description:
 */
public class PageBean<T> {
    private int pageNo = 1;//当前页数,默认为1
    private int pageSize = 10;//每页行数
    private int rowCount;//共有行数
    private int totalPage;//共有页数
    private int startIndex = 0;//limit起始索引
    private String[] factor;//查询条件数组
    private List<T> data;//查询的数据

    public PageBean() {
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage() {
        this.totalPage = rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize + 1;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String[] getFactor() {
        return factor;
    }

    public void setFactor(String[] factor) {
        this.factor = factor;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
