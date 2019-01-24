package com.zack.kongtv.activities.SearchResult;

import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zackdk.mvp.v.IView;

import java.util.List;

public interface ISearchView extends IView {
    public void updateView(List<Cms_movie> data);
    void loadMoreComplete();
    void loadMoreEnd();
}
