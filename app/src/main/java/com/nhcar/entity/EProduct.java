package com.nhcar.entity;

public class EProduct {
    private int pid;
    private String pname;
    private float pprice;
    private String ppic;

    public EProduct(){
    }

    public EProduct(int pid, String pname, float pprice, String ppic) {
        this.pid = pid;
        this.pname = pname;
        this.pprice = pprice;
        this.ppic = ppic;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public float getPprice() {
        return pprice;
    }

    public void setPprice(float pprice) {
        this.pprice = pprice;
    }

    public String getPpic() {
        return ppic;
    }

    public void setPpic(String ppic) {
        this.ppic = ppic;
    }
}
