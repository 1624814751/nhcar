package com.nhcar.entity;

public class ECarNews {
    private int nid;
    private String ntitle;
    private String uname;
    private String npic;
    private String ndate;

    public ECarNews(){

    }

    public ECarNews(int nid, String ntitle, String uname, String npic, String ndate) {
        this.nid = nid;
        this.ntitle = ntitle;
        this.uname = uname;
        this.npic = npic;
        this.ndate = ndate;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getNpic() {
        return npic;
    }

    public void setNpic(String npic) {
        this.npic = npic;
    }

    public String getNdate() {
        return ndate;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }
}
