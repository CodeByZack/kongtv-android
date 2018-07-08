package com.zack.kongtv.activities.PlayMovie;


import com.zack.kongtv.Data.DataResp;
import com.zackdk.mvp.p.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlayMoviePresenter<V extends IPlayMovieView> extends BasePresenter<V> {

    public void requestData(String url) {
        getView().showLoading();
        DataResp.getPlayUrl(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getView().play(s);
                    }
                });
    }
}
