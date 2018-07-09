package com.zack.kongtv.activities.SearchResult;

import com.zack.kongtv.bean.MovieDetailBean;
import com.zackdk.mvp.v.IView;

import java.util.List;

public interface ISearchView extends IView {
    public void updateView(List<MovieDetailBean> data);
    void loadMoreComplete();
    void loadMoreEnd();
}
