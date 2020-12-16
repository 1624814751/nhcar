package com.nhcar.entity;

import android.content.Intent;

public class EComment {
    private int cid;
    private int cpid;
    private String cname;
    private String ccontent;
    private String date;

    public EComment(){

    }

    public EComment(int cid, int cpid, String cname, String ccontent,String date) {
        this.cid = cid;
        this.cpid = cpid;
        this.cname = cname;
        this.ccontent = ccontent;
        this.date=date;
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

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCcontent() {
        return ccontent;
    }

    public void setCcontent(String ccontent) {
        this.ccontent = ccontent;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date=date;
    }
}
