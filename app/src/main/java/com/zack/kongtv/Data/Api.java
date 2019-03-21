package com.zack.kongtv.Data;


import com.zack.kongtv.bean.Cms_movie;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Api {

    @GET("api.php")
    Observable<List<Cms_movie>> getList(@QueryMap HashMap<String,String> map);

    @GET("api.php")
    Observable<List<Cms_movie>> getDY(@QueryMap HashMap<String, String> paramsMap);

    @GET("api.php")
    Observable<List<Cms_movie>> getDSJ(@QueryMap HashMap<String, String> paramsMap);

    @GET("api.php")
    Observable<List<Cms_movie>> getDM(@QueryMap HashMap<String, String> paramsMap);

    @GET("api.php")
    Observable<List<Cms_movie>> getZY(@QueryMap HashMap<String, String> paramsMap);


    @GET("api.php")
    Observable<List<Cms_movie>> search(@QueryMap HashMap<String, String> paramsMap);
}
