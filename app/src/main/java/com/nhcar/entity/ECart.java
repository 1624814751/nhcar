package com.nhcar.entity;

import java.util.Date;

public class ECart {
    private int cid;
    private int cpid;
    private int cnum;
    private int cuid;
    private Date cdate;

    public ECart(){

    }

    public ECart(int cid, int cpid, int cnum, int cuid, Date cdate) {
        this.cid = cid;
        this.cpid = cpid;
        this.cnum = cnum;
        this.cuid = cuid;
        this.cdate = cdate;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCpid() {
        return cpid;
    }

    public void setCpid(int cpid) {
        this.cpid = cpid;
    }

    public int getCnum() {
        return cnum;
    }

    public void setCnum(int cnum) {
        this.cnum = cnum;
    }

    public int getCuid() {
        return cuid;
    }

    public void setCuid(int cuid) {
        this.cuid = cuid;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }
}
