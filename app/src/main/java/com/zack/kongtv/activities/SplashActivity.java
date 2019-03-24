package com.zack.kongtv.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zack.appupdate.AppUpdate;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.R;
import com.zack.kongtv.bean.AppConfig;
import com.zack.kongtv.bean.UpdateInfo;
import com.zack.kongtv.util.PackageUtil;
import com.zackdk.Utils.LogUtil;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    private Disposable updateInfoDisposable;
    private AppUpdate appupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        updateInfoDisposable = DataResp.getAppConfig().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<AppConfig>() {
                    @Override
                    public void accept(AppConfig o) throws Exception {
                        LogUtil.d2(o);
                        if(o.getmAppCode() > PackageUtil.packageCode(SplashActivity.this)){
                            showUpdateDilog(o);
                        }else{
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                                    finish();
                                }
                            }, 3000);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(throwable.getLocalizedMessage());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                                finish();
                            }
                        }, 3000);
                    }
                });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(updateInfoDisposable!=null &&!updateInfoDisposable.isDisposed())updateInfoDisposable.dispose();
        if(appupdate!=null)appupdate.dismiss();

    }


    private void showUpdateDilog(AppConfig updateInfo) {
        String dirFilePath = "";
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            //SD卡有用
            dirFilePath = getExternalFilesDir("apk/test.apk").getAbsolutePath();
        }else{
            //SD卡没有用
            dirFilePath = getFilesDir()+ File.separator+"apk/test.apk";
        }
        appupdate = AppUpdate.init(this)
                .setDownloadUrl(updateInfo.getAppUrl())
                .setSavePath(dirFilePath);
        appupdate.showUpdateDialog("检查到有更新！", updateInfo.getAppMate(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        });
    }

}
