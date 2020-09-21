package com.entry;

public class PageEntity {
    private int pageNow;
    private int pageRows;
    private int rowsCount;
    private int pagesCount;
    private Object pageData;

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageRows() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public Object getPageData() {
        return pageData;
    }

    public void setPageData(Object pageData) {
        this.pageData = pageData;
    }
}
