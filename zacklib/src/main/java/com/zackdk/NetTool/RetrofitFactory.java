package com.zackdk.NetTool;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class RetrofitFactory {
    /*private static RetrofitFactory instance;
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
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(initClient())
                .build();
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

    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }*/
}
