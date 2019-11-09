package com.zackdk.NetTool;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitFactory {
    private static RetrofitFactory instance;
    private static Retrofit retrofit;
    private static Interceptor interceptor;

    private RetrofitFactory() {
        //拦截器
        interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content_Type","application/json")
                        .addHeader("charset","UTF-8")
                        //.addHeader("token",AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                        .build();
                return chain.proceed(request);
            }
        };

        retrofit = new Retrofit.Builder()
//                .baseUrl("http://65.49.209.21:5000/")
                .baseUrl("http://47.94.254.236:55/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    private OkHttpClient initClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(initLogInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

    }

    private Interceptor initLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public static RetrofitFactory getInstance() {
        if(instance==null){
            synchronized (RetrofitFactory.class){
                if(instance == null){
                    instance = new RetrofitFactory();
                }
            }
        }
        return instance;
    }

//    public static  <T> T create(Class<T> service){
//        return getInstance().create(service);
//    }
}
