package com.nhcar.entity;

import java.util.List;

public class EProductListResult {
    private int pageSize;//每页行数
    private int dataCount;//总行数
    private int start;//开始序号
    private int pageCount;//总页数
    private int page;//当前页码
    private List<EProduct> dataResult;//当前页数据

    public EProductListResult(){

    }

    public EProductListResult(int pageSize, int dataCount, int start, int pageCount, int page) {
        this.pageSize = pageSize;
        this.dataCount = dataCount;
        this.start = start;
        this.pageCount = pageCount;
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<EProduct> getDataResult() {
        return dataResult;
    }

    public void setDataResult(List<EProduct> dataResult) {
        this.dataResult = dataResult;
    }
}
