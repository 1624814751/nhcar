package com.nhcar.entity;

public class ECategory {
    private int cid;
    private String cname;
    private String cpic;

    public ECategory(){

    }
    public ECategory(int cid, String cname, String cpic) {
        this.cid = cid;
        this.cname = cname;
        this.cpic = cpic;
    }


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCpic() {
        return cpic;
    }

    public void setCpic(String cpic) {
        this.cpic = cpic;
    }
}
