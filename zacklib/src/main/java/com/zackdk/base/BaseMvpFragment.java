package com.zackdk.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zackdk.customview.R;
import com.zackdk.mvp.p.BasePresenter;
import com.zackdk.mvp.v.IView;

public abstract class BaseMvpFragment<T extends BasePresenter> extends AbsFragment implements IView {
    protected T presenter;
    private MaterialDialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = setPresenter();
        presenter.attachView(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    protected abstract T setPresenter();

    @Override
    public void showToast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        loadingDialog = new MaterialDialog.Builder(mActivity)
                .content("加载中...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }

    @Override
    public void showLoading(String msg) {

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
}
