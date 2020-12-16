package com.nhcar.entity;

import java.util.List;

public class ECommentResult {
    private int pid;//汽车ID
    private int page;//当前页码
    private int pageSize;//每页行数
    private List<EComment> dataResult;//当前页数据

    public ECommentResult(){

    }


    public ECommentResult(int pid, int page, int pageSize) {
        this.pid = pid;
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<EComment> getDataResult() {
        return dataResult;
    }

    public void setDataResult(List<EComment> dataResult) {
        this.dataResult = dataResult;
    }
}
