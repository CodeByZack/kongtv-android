package com.zack.kongtv.bean;

import java.io.Serializable;

public class JujiBean implements Serializable {
    private String url,text;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
