package com.zackdk.mvp.v;

public interface IView {
    void showToast(String message);

    void showLoading();

    void hideLoading();

    void onNetError();

}
