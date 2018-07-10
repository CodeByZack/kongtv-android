package com.zack.kongtv;

import android.app.Application;
import android.content.Context;
import android.util.Log;


public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
