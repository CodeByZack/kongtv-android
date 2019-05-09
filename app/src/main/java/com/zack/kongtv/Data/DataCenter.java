package com.zack.kongtv.Data;

import com.zack.kongtv.Data.Instance.HtmlResovle;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.JujiBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DataCenter {

    public static Observable getHomeData(){
        Observable<List<Cms_movie>> observable = Observable.create(new ObservableOnSubscribe<List<Cms_movie>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Cms_movie>> emitter) throws Exception {
                List<Cms_movie> data = null;
                data = HtmlResovle.getHomeData();
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
//                    emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }

    public static Observable getRealMovieData(final Cms_movie cms_movie){
        Observable<Cms_movie> observable = Observable.create(new ObservableOnSubscribe<Cms_movie>() {
            @Override
            public void subscribe(ObservableEmitter<Cms_movie> emitter) throws Exception {
                Cms_movie data = null;
                data = HtmlResovle.getRealMovieDetail(cms_movie);
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{

                }
            }
        });

        return observable;
    }

    public static Observable getRealPlayUrl(final String url){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String playUrl = HtmlResovle.getRealPlayUrl(url);
                emitter.onNext(playUrl);
                emitter.onComplete();
            }
        });
        return observable;
    }
}
