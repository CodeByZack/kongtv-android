package com.zack.kongtv.bean;

import java.util.List;

public class HomeDataBean {
    private List<HomeItemBean> homeItemBeans;
    private List<BannerItemBean> bannerItemBeans;

    public List<HomeItemBean> getHomeItemBeans() {
        return homeItemBeans;
    }

    public void setHomeItemBeans(List<HomeItemBean> homeItemBeans) {
        this.homeItemBeans = homeItemBeans;
    }

    public List<BannerItemBean> getBannerItemBeans() {
        return bannerItemBeans;
    }

    public void setBannerItemBeans(List<BannerItemBean> bannerItemBeans) {
        this.bannerItemBeans = bannerItemBeans;
    }
}
