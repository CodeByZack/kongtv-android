package com.zack.kongtv.Data;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.zack.kongtv.App;
import com.zack.kongtv.Const;
import com.zack.kongtv.Data.Instance.GetDataInterface;
import com.zack.kongtv.activities.MainActivity;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;
import com.zack.kongtv.bean.UpdateInfo;
import com.zackdk.Utils.SPUtil;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DataResp {


    public static Observable getHomeData(){
        HashMap<String,String> map = new HashMap<>();
        map.put("router","index");
        Observable<List<Cms_movie>> index_data = NetTool.getInstance().getList(map);
        return index_data;
    }
    public static Observable getFilmData(String year,String area,String classname,int page){
        HashMap<String, String> map = NetTool.getParamMap(year,area,classname,page);
        map.put("router","dy");
        Observable<List<Cms_movie>> observable = NetTool.getInstance().getDY(map);
        return observable;
    }
    public static Observable getEpisodeData(String year,String area,String classname,int page){
        HashMap<String, String> map = NetTool.getParamMap(year,area,classname,page);
        map.put("router","dsj");
        Observable<List<Cms_movie>> observable = NetTool.getInstance().getDSJ(map);
        return observable;
    }
    public static Observable getAnimeData(String year,String area,String classname,int page){
        HashMap<String, String> map = NetTool.getParamMap(year,area,classname,page);
        map.put("router","dm");
        Observable<List<Cms_movie>> observable = NetTool.getInstance().getDM(map);
        return observable;
    }
    public static Observable getVarietyData(String year,String area,String classname,int page){
        HashMap<String, String> map = NetTool.getParamMap(year,area,classname,page);
        map.put("router","zy");
        Observable<List<Cms_movie>> observable = NetTool.getInstance().getZY(map);
        return observable;
    }

    public static Observable searchText(final  String text,final int page){
        HashMap<String,String> map = new HashMap<>();
        map.put("router","search");
        map.put("name",text);
        Observable<List<Cms_movie>> observable = NetTool.getInstance().search(map);
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
