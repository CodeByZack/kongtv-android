package com.zack.kongtv.bean;

public class UpdateInfo {
    private String app_name;
    private String app_updateInfo;

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    private String download_url;
    private int app_version;
    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public int getApp_version() {
        return app_version;
    }

    public void setApp_version(int app_version) {
        this.app_version = app_version;
    }

    public String getApp_updateInfo() {
        return app_updateInfo;
    }

    public void setApp_updateInfo(String app_updateInfo) {
        this.app_updateInfo = app_updateInfo;
    }
}
