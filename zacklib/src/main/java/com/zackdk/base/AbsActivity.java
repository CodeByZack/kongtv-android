package com.zackdk.base;

/**
 * Created by Zackv on 2018/4/2.
 */

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zackdk.Utils.LogUtil;
import com.zackdk.customview.R;

public abstract class AbsActivity extends BaseActivity {

    protected Activity mActivity;
    protected boolean printLifeCycle = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivity = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        overridePendingTransition(R.anim.push_pic_left_in, R.anim.push_pic_left_out);
        final int vid = setView();
        super.onCreate(savedInstanceState);
        if (vid != 0) {
            setContentView(vid);
            initBasic(savedInstanceState);
        }
        if (printLifeCycle) {
            LogUtil.d( "onCreate");
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_pic_right_in, R.anim.push_pic_right_out);
    }

    public abstract int setView();

    public abstract void initBasic(Bundle savedInstanceState);

    @Override
    protected void onStart() {
        super.onStart();
        if (printLifeCycle) {
            LogUtil.d( "onStart");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (printLifeCycle) {
            LogUtil.d( "onResume");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (printLifeCycle) {
            LogUtil.d( "onPause");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (printLifeCycle) {
            LogUtil.d( "onStop");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (printLifeCycle) {
            LogUtil.d( "onRestart");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (printLifeCycle) {
            LogUtil.d("onDestroy");
        }
    }

}

