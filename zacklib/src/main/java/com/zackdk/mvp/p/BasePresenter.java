package com.zackdk.mvp.p;


import com.zackdk.mvp.v.IView;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends IView> {
    private List<Disposable> disposableList;
    /**
     * 绑定的view
     */
    private V mvpView;

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void detachView() {
        this.mvpView = null;
        //取消所有请求
        if(disposableList!=null){
            for (Disposable d: disposableList) {
                if(!d.isDisposed()){
                    d.dispose();
                }
            }
        }
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return mvpView != null;
    }

    /**
     * 获取连接的view
     */
    public V getView() {
        return mvpView;
    }

    public void addDispoasble(Disposable disposable){
        if(disposableList == null){
            disposableList = new LinkedList<>();
        }
        disposableList.add(disposable);
    }
}
