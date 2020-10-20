package com.nhcar.entity;

public class EUser {
    private int uid;
    private String uname;
    private String uemail;
    private String upwd;
    private String urepwd;
    private String ulogo;

    public EUser(){

    }

    public EUser(int uid, String uname, String uemail, String upwd, String urepwd, String ulogo) {
        this.uid = uid;
        this.uname = uname;
        this.uemail = uemail;
        this.upwd = upwd;
        this.urepwd = urepwd;
        this.ulogo = ulogo;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public String getUrepwd() {
        return urepwd;
    }

    public void setUrepwd(String urepwd) {
        this.urepwd = urepwd;
    }

    public String getUlogo() {
        return ulogo;
    }

    public void setUlogo(String ulogo) {
        this.ulogo = ulogo;
    }
}
