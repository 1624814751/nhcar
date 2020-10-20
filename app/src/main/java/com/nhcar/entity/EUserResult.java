package com.nhcar.entity;

public class EUserResult {
    private int result;
    private String msg;

    public EUserResult(){

    }

    public EUserResult(int result, String msg) {
        this.result = result;
        this.msg = msg;
    }


    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
