package com.zack.kongtv.fragments.Category;

import android.text.TextUtils;

import com.zack.kongtv.Data.DataResp;
import com.zack.kongtv.R;
import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.MovieItemBean;
import com.zack.kongtv.bean.TagItemBean;
import com.zackdk.Utils.LogUtil;
import com.zackdk.mvp.p.BasePresenter;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoryPresenter<V extends ICategoryView> extends BasePresenter<V> {
    private int page = 1;
    private String targetUrl;
    public void requestData(){
        if(TextUtils.isEmpty(targetUrl)){
            getView().showToast("请求地址错误！");
            return;
        }
        getView().showLoading();
        Disposable d = DataResp.getTypeData(targetUrl, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CategoryDataBean>() {
                    @Override
                    public void accept(CategoryDataBean dataBean) throws Exception {
                        if (dataBean.getMovieDetailBeans().size() != 0) {
                            getView().updateView(dataBean);
                            page++;
                            getView().loadMoreComplete();
                        } else {
                            getView().loadMoreEnd();
                        }
                        getView().hideLoading();
                    }
                });
        addDispoasble(d);
    }

    public void loadMore() {
        requestData();
    }

    public void setTargetUrl(String targetUrl) {
        //改变了分类，重置页数
        this.targetUrl = targetUrl;
        page = 1;
    }

    public void refresh() {
        page = 1;
        requestData();
    }
}
