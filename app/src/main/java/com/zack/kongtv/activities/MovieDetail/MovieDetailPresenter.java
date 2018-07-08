package com.zack.kongtv.activities.MovieDetail;

import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zackdk.mvp.p.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailPresenter<V extends IMovieDetailView> extends BasePresenter<V> {
    public void requestData(String url) {
        DataResp.getMovieDetail(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<MovieDetailBean>() {
                    @Override
                    public void accept(MovieDetailBean movieDetailBean) throws Exception {
                        getView().updateView(movieDetailBean);
                    }
                });
    }
}
