package com.zack.kongtv.activities.SearchResult;

import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.Data.ErrConsumer;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;
import com.zackdk.mvp.p.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter<V extends ISearchView> extends BasePresenter<V> {
    private int page = 1;
    public void search(String text) {
        getView().showLoading();
        Disposable d = DataResp.searchText(text, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cms_movie>>() {
                    @Override
                    public void accept(List<Cms_movie> cms_movies) throws Exception {
                        getView().updateView(cms_movies);
                        getView().hideLoading();
                        getView().loadMoreEnd();
                    }
                },new ErrConsumer());
        addDispoasble(d);
    }

    public void clear() {
        page = 1;
    }
}
