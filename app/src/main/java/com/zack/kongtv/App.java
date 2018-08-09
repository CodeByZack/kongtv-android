package com.zack.kongtv;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.zack.kongtv.Data.DataResp;
import com.zackdk.NetWorkChange.NetStateChangeReceiver;
import com.zackdk.Utils.LogUtil;

import java.util.LinkedList;
import java.util.List;


public class App extends Application {
    private static Context context;
    private static List<Activity> activities = new LinkedList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initUM();
        QbSdk.initX5Environment(this,null);
        DataResp.initInstaceList();
        NetStateChangeReceiver.registerReceiver(this);
        registerActivityLifecycleCallbacks(life);
        MultiDex.install(this);
    }

    private void initUM() {
        UMConfigure.init(this, "5b460ddfa40fa35036000318", "default", UMConfigure.DEVICE_TYPE_PHONE, "");
        if(BuildConfig.DEBUG){
            UMConfigure.setLogEnabled(true);
        }
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_DUM_NORMAL);
        //MobclickAgent.openActivityDurationTrack(false);
    }

    public static Context getContext() {
        return context;
    }


    private ActivityLifecycleCallbacks life = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activities.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            //MobclickAgent.onPageStart(this.getClass().getName());
            MobclickAgent.onResume(activity);
            LogUtil.d(activity.getLocalClassName()+"dkdkonResume");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            //MobclickAgent.onPageEnd(this.getClass().getName());
            MobclickAgent.onPause(activity);
            LogUtil.d(activity.getLocalClassName()+"dkdkonPause");
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activities.remove(activity);
        }
    };

    @Override
    public void onTerminate() {
        NetStateChangeReceiver.unregisterReceiver(this);
        super.onTerminate();

    }

    public static void finshAllActivity(){
        for (Activity a:activities) {
            a.finish();
        }
    }
}
