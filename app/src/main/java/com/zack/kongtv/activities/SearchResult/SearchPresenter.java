package com.zack.kongtv.activities.SearchResult;

import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;
import com.zackdk.mvp.p.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter<V extends ISearchView> extends BasePresenter<V> {
    private int page = 1;
    public void search(String text) {
        getView().showLoading();
        DataResp.searchText(text,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<SearchResultBean>() {
                    @Override
                    public void accept(SearchResultBean movieDetailBeans) throws Exception {
                        getView().updateView(movieDetailBeans.getList());
                        if(movieDetailBeans.isCanLoadMore()){
                            getView().loadMoreComplete();
                            page++;
                        }else{
                            getView().loadMoreEnd();
                        }
                        getView().hideLoading();
                    }
                });
    }

    public void clear() {
        page = 1;
    }
}
