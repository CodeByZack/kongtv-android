package com.zack.kongtv.bean;

import java.util.List;

public class SearchResultBean {
    private List<MovieDetailBean> list;
    private boolean canLoadMore;

    public List<MovieDetailBean> getList() {
        return list;
    }

    public void setList(List<MovieDetailBean> list) {
        this.list = list;
    }

    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }
}
