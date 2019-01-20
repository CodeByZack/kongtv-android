package com.zack.kongtv.bean;

import java.util.List;

public class HomeDataBean {
    private List<HomeItemBean> homeItemBeans;
    private List<Cms_movie> bannerItemBeans;

    public List<HomeItemBean> getHomeItemBeans() {
        return homeItemBeans;
    }

    public void setHomeItemBeans(List<HomeItemBean> homeItemBeans) {
        this.homeItemBeans = homeItemBeans;
    }


    public List<Cms_movie> getBannerItemBeans() {
        return bannerItemBeans;
    }

    public void setBannerItemBeans(List<Cms_movie> bannerItemBeans) {
        this.bannerItemBeans = bannerItemBeans;
    }
}
