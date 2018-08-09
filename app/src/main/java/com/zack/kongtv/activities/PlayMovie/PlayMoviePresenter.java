package com.zack.kongtv.activities.PlayMovie;


import com.zack.kongtv.Const;
import com.zack.kongtv.Data.DataResp;
import com.zackdk.Utils.LogUtil;
import com.zackdk.mvp.p.BasePresenter;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlayMoviePresenter<V extends IPlayMovieView> extends BasePresenter<V> {

    private String[] msgs = {"维护不易，不妨捐助一波...","多试试X5播放器...","我本将心向明月，奈何明月照沟渠"};

    public void requestData(String url) {
        getView().showLoading(getMsg());
        Disposable d = DataResp.getPlayUrl(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getView().play(s);
                    }
                });
        addDispoasble(d);
    }

    private String getMsg(){
        int length = msgs.length;
        int x=(int)(Math.random()*length);
        if(x<length){
            return msgs[x];
        }else{
            return "";
        }
    }
}
