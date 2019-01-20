package com.zack.kongtv.Data;


import com.zack.kongtv.bean.Cms_movie;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Api {

    @GET("index")
    Observable<List<Cms_movie>> getList();

    @GET("dy")
    Observable<List<Cms_movie>> getDY(@QueryMap HashMap<String, String> paramsMap);

    @GET("dsj")
    Observable<List<Cms_movie>> getDSJ(@QueryMap HashMap<String, String> paramsMap);

    @GET("dm")
    Observable<List<Cms_movie>> getDM(@QueryMap HashMap<String, String> paramsMap);

    @GET("zy")
    Observable<List<Cms_movie>> getZY(@QueryMap HashMap<String, String> paramsMap);
}
