package com.zackdk.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zackdk.customview.R;
import com.zackdk.mvp.p.BasePresenter;
import com.zackdk.mvp.v.IView;

public abstract class BaseMvpActivity<T extends BasePresenter> extends AbsActivity implements IView {
    protected T presenter;
    private MaterialDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = setPresenter();
        presenter.attachView(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        loadingDialog = new MaterialDialog.Builder(this)
                .content("加载中...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }

    @Override
    public void hideLoading() {
        if(loadingDialog != null){
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void onNetError() {
        showToast("网络错误！");
    }

    protected abstract T setPresenter();

}
