package com.zack.kongtv.activities.MovieDetail;

import com.umeng.commonsdk.debug.D;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.Data.room.CollectMovie;
import com.zack.kongtv.Data.room.DataBase;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zackdk.mvp.p.BasePresenter;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MovieDetailPresenter<V extends IMovieDetailView> extends BasePresenter<V> {
    public void requestData(String url) {
        Disposable d = DataResp.getMovieDetail(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<MovieDetailBean>() {
                    @Override
                    public void accept(MovieDetailBean movieDetailBean) throws Exception {
                        getView().updateView(movieDetailBean);
                    }
                });
        addDispoasble(d);
        CollectMovie data = DataBase.getInstance().collectMovieDao().findByTargetUrl(url);
        if(data!=null){
            getView().collect(true);
        }else{
            getView().collect(false);
        }
    }
}
