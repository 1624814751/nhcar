package com.nhcar.entity;

public class ECartltem {
    private int cid;
    private int pid;
    private int cnum;
    private double pprice;
    private String ppic;
    private String pname;

    public ECartltem(){

    }

    public ECartltem(int cid, int pid, int cnum, double pprice, String ppic, String pname) {
        this.cid = cid;
        this.pid = pid;
        this.cnum = cnum;
        this.pprice = pprice;
        this.ppic = ppic;
        this.pname = pname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCnum() {
        return cnum;
    }

    public void setCnum(int cnum) {
        this.cnum = cnum;
    }

    public double getPprice() {
        return pprice;
    }

    public void setPprice(double pprice) {
        this.pprice = pprice;
    }

    public String getPpic() {
        return ppic;
    }

    public void setPpic(String ppic) {
        this.ppic = ppic;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
