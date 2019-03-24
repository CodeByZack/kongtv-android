
package com.zack.kongtv.bean;

import com.google.gson.annotations.SerializedName;

public class AppConfig {

    @SerializedName("ad_player_img")
    private String mAdPlayerImg;
    @SerializedName("ad_player_time")
    private String mAdPlayerTime;
    @SerializedName("ad_player_url")
    private String mAdPlayerUrl;
    @SerializedName("ad_splash_img")
    private String mAdSplashImg;
    @SerializedName("ad_splash_url")
    private String mAdSplashUrl;
    @SerializedName("app_close_tip")
    private String mAppCloseTip;
    @SerializedName("app_img")
    private String mAppImg;
    @SerializedName("app_mate")
    private String mAppMate;
    @SerializedName("app_name")
    private String mAppName;
    @SerializedName("app_status")
    private String mAppStatus;
    @SerializedName("app_url")
    private String mAppUrl;
    @SerializedName("app_version")
    private String mAppVersion;
    @SerializedName("app_code")
    private int mAppCode;

    public int getmAppCode() {
        return mAppCode;
    }

    public void setmAppCode(int mAppCode) {
        this.mAppCode = mAppCode;
    }

    public String getAdPlayerImg() {
        return mAdPlayerImg;
    }

    public void setAdPlayerImg(String adPlayerImg) {
        mAdPlayerImg = adPlayerImg;
    }

    public String getAdPlayerTime() {
        return mAdPlayerTime;
    }

    public void setAdPlayerTime(String adPlayerTime) {
        mAdPlayerTime = adPlayerTime;
    }

    public String getAdPlayerUrl() {
        return mAdPlayerUrl;
    }

    public void setAdPlayerUrl(String adPlayerUrl) {
        mAdPlayerUrl = adPlayerUrl;
    }

    public String getAdSplashImg() {
        return mAdSplashImg;
    }

    public void setAdSplashImg(String adSplashImg) {
        mAdSplashImg = adSplashImg;
    }

    public String getAdSplashUrl() {
        return mAdSplashUrl;
    }

    public void setAdSplashUrl(String adSplashUrl) {
        mAdSplashUrl = adSplashUrl;
    }

    public String getAppCloseTip() {
        return mAppCloseTip;
    }

    public void setAppCloseTip(String appCloseTip) {
        mAppCloseTip = appCloseTip;
    }

    public String getAppImg() {
        return mAppImg;
    }

    public void setAppImg(String appImg) {
        mAppImg = appImg;
    }

    public String getAppMate() {
        return mAppMate;
    }

    public void setAppMate(String appMate) {
        mAppMate = appMate;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }

    public String getAppStatus() {
        return mAppStatus;
    }

    public void setAppStatus(String appStatus) {
        mAppStatus = appStatus;
    }

    public String getAppUrl() {
        return mAppUrl;
    }

    public void setAppUrl(String appUrl) {
        mAppUrl = appUrl;
    }

    public String getAppVersion() {
        return mAppVersion;
    }

    public void setAppVersion(String appVersion) {
        mAppVersion = appVersion;
    }

}
