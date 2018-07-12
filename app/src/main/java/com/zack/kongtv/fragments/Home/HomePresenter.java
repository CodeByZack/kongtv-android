package com.zack.kongtv.fragments.Home;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.R;
import com.zack.kongtv.bean.BannerItemBean;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.HomeItemBean;
import com.zackdk.mvp.p.BasePresenter;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter<T extends IHomeView> extends BasePresenter<T> {
    public void requestData() {
        getView().showLoading();
        DataResp.getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeDataBean>() {
                    @Override
                    public void accept(HomeDataBean dataBean) throws Exception {
                        getView().updateView(dataBean);
                        getView().hideLoading();
                    }
                });
    }

    public void refresh() {
        requestData();
        getView().showToast("刷新成功！");
        getView().setRefresh(false);
    }
}
