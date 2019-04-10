package com.example.camera.cameralibrary.ottoutils;

/**
 * Created by admin on 2017/6/8.
 */

public class SendNews {
    private  String ss;
    private String type;

    public SendNews(String ss,String type) {
        this.ss = ss;
        this.type=type;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
