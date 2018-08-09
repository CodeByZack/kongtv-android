package com.zack.kongtv.Data;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.zack.kongtv.App;
import com.zack.kongtv.AppConfig;
import com.zack.kongtv.Const;
import com.zack.kongtv.Data.Instance.GetDataInterface;
import com.zack.kongtv.Data.Instance.Impl_4kwu;
import com.zack.kongtv.Data.Instance.Impl_kankanwu;
import com.zack.kongtv.Data.Instance.Impl_pipigui;
import com.zack.kongtv.Data.Instance.Impl_yimimao;
import com.zack.kongtv.activities.MainActivity;
import com.zack.kongtv.bean.BannerItemBean;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.HomeItemBean;
import com.zack.kongtv.bean.JujiBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;
import com.zack.kongtv.bean.TagItemBean;
import com.zack.kongtv.bean.UpdateInfo;
import com.zackdk.Utils.LogUtil;
import com.zackdk.Utils.SPUtil;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DataResp {

    public static Map<String,GetDataInterface> ALL_INSTANCE = new HashMap<>();
    public static GetDataInterface INSTANCE;
    private static final String XIANLU = "XIANLU_NAME";
    //统一提供URL
    public static  String baseUrl = "";
    public static  String MovieUrl = "";
    public static  String EpisodeUrl = "";
    public static  String AnimeUrl = "";
    public static  String VarietyUrl = "";
    public static  String SearchUrl = "";

    public static void initInstaceList(){
        //创建不同线路实例
        GetDataInterface Impl4kwu = new Impl_4kwu();
        GetDataInterface Implkankanwu = new Impl_kankanwu();
        GetDataInterface Implpipigui = new Impl_pipigui();
        GetDataInterface Implyimimao = new Impl_yimimao();

        //存入map;其实可以按需创建实列；
        ALL_INSTANCE.put(Impl4kwu.getName(),Impl4kwu);
        ALL_INSTANCE.put(Implkankanwu.getName(),Implkankanwu);
        ALL_INSTANCE.put(Implpipigui.getName(),Implpipigui);
        ALL_INSTANCE.put(Implyimimao.getName(),Implyimimao);

        //获取之前储存线路，指定当前实例
        String mapkay = (String) SPUtil.getData(App.getContext(),XIANLU,Implpipigui.getName());
        INSTANCE = ALL_INSTANCE.get(mapkay);
        //初始化url
        baseUrl = INSTANCE.getBaseUrl();
        MovieUrl = INSTANCE.getMovieUrl();
        EpisodeUrl = INSTANCE.getEpisodeUrl();
        AnimeUrl = INSTANCE.getAnimeUrl();
        VarietyUrl = INSTANCE.getVarietyUrl();
        SearchUrl = INSTANCE.getSearchUrl();
    }
    public static void changeInstance(Context context,String mapkey) {
        //切换线路实例
        INSTANCE = ALL_INSTANCE.get(mapkey);

        //储存线路名字
        SPUtil.saveDate(context,XIANLU,mapkey);

        //切换网址
        baseUrl = INSTANCE.getBaseUrl();
        MovieUrl = INSTANCE.getMovieUrl();
        EpisodeUrl = INSTANCE.getEpisodeUrl();
        AnimeUrl = INSTANCE.getAnimeUrl();
        VarietyUrl = INSTANCE.getVarietyUrl();
        SearchUrl = INSTANCE.getSearchUrl();

        //重启应用
        App.finshAllActivity();
        final Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static Observable getHomeData(){
        Observable<HomeDataBean> observable = Observable.create(new ObservableOnSubscribe<HomeDataBean>() {
            @Override
            public void subscribe(ObservableEmitter<HomeDataBean> emitter) throws Exception {
                HomeDataBean data = null;
                data = INSTANCE.getHomeData();
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
    public static Observable getTypeData(final String url, final int page){
        Observable<CategoryDataBean> observable = Observable.create(new ObservableOnSubscribe<CategoryDataBean>() {
            @Override
            public void subscribe(ObservableEmitter<CategoryDataBean> emitter) throws Exception {
                CategoryDataBean data = INSTANCE.getCategoryData(url,page);
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
                    //emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }
    public static Observable getMovieDetail(final String url){
        Observable<MovieDetailBean> observable = Observable.create(new ObservableOnSubscribe<MovieDetailBean>() {
            @Override
            public void subscribe(ObservableEmitter<MovieDetailBean> emitter) throws Exception {
                MovieDetailBean data = INSTANCE.getRealMovieDetail(url);
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
                    //emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }
    public static Observable getPlayUrl(final String url){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String data = INSTANCE.getRealPlayUrl(url);
                if(data!=null){
                    emitter.onNext(data);
                    emitter.onComplete();
                }else{
                    //emitter.onError(new Throwable("解析出错!"));
                }
            }
        });
        return observable;
    }
    public static Observable searchText(final  String text,final int page){
        Observable<SearchResultBean> observable = Observable.create(new ObservableOnSubscribe<SearchResultBean>() {
            @Override
            public void subscribe(ObservableEmitter<SearchResultBean> emitter) throws Exception {
                SearchResultBean data = INSTANCE.search(text,page);
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


    public static Observable getAppUpdateInfo(){
        Observable<UpdateInfo> observable = Observable.create(new ObservableOnSubscribe<UpdateInfo>() {
            @Override
            public void subscribe(ObservableEmitter<UpdateInfo> emitter) throws Exception {
                Connection.Response body = Jsoup.connect(Const.CHECK_UPDATE_URL)
                        .header("Accept", "*/*")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                        .ignoreContentType(true).execute();
                String res = body.body();
                JSONObject json = new JSONObject(res);
                Log.d("", "subscribe: "+json);
                UpdateInfo updateInfo = new UpdateInfo();
                String name = json.getString("app_name");
                String updateinfo = json.getString("app_updateInfo");
                String download_url = json.getString("download_url");
                int version = json.getInt("app_version");

                if(!TextUtils.isEmpty(name)){
                    updateInfo.setApp_name(name);
                }
                if(!TextUtils.isEmpty(updateinfo)){
                    updateInfo.setApp_updateInfo(updateinfo);
                }
                if(!TextUtils.isEmpty(download_url)){
                    updateInfo.setDownload_url(download_url);
                }
                updateInfo.setApp_version(version);

                emitter.onNext(updateInfo);
                emitter.onComplete();
            }
        });
        return observable;
    }
}
