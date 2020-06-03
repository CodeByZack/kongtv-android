package com.zackdk.base;

/**
 * Created by Zackv on 2018/4/2.
 */

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;

import androidx.annotation.Nullable;

public abstract class AbsActivity extends BaseActivity {

    protected Activity mActivity;
    protected ImmersionBar immersionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivity = this;
        immersionBar = ImmersionBar.with(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
//        overridePendingTransition(R.anim.push_pic_left_in, R.anim.push_pic_left_out);
        final int vid = setView();
        super.onCreate(savedInstanceState);
        if (vid != 0) {
            setContentView(vid);
            initBasic(savedInstanceState);
        }
        initImmersionBar();
    }

    protected void initImmersionBar() {
        immersionBar.init();
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.push_pic_right_in, R.anim.push_pic_right_out);
    }

    public abstract int setView();

    public abstract void initBasic(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        immersionBar.destroy();
    }

}

