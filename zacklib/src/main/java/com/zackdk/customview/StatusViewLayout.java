package com.zackdk.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2018/2/24.
 */

public class StatusViewLayout extends FrameLayout {

    private View emptyView;
    private View errorView;
    private View loadingView;


    public StatusViewLayout(Context context) {
        super(context);
    }

    public StatusViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StatusViewLayout, 0, 0);
        try {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            int errorViewID = a.getResourceId(R.styleable.StatusViewLayout_errorView, R.layout.error_layout);
            int loadingViewID = a.getResourceId(R.styleable.StatusViewLayout_loadingView, R.layout.loading_layout);
            int emptyViewID = a.getResourceId(R.styleable.StatusViewLayout_emptyView, R.layout.empty_layout);
            emptyView = inflater.inflate(emptyViewID,this,false);
            errorView = inflater.inflate(errorViewID,this,false);
            loadingView = inflater.inflate(loadingViewID,this,false);
            addView(emptyView);
            addView(errorView);
            addView(loadingView);
        } finally {
            a.recycle();
        }
    }

    public void showEmptyView(){
        hideAllViews();
        emptyView.setVisibility(VISIBLE);
    }

    public void showLoadingView(){
        hideAllViews();
        loadingView.setVisibility(VISIBLE);
    }

    public void showErrorView(){
        hideAllViews();
        errorView.setVisibility(VISIBLE);
    }

    public void showContentView(){
        int childCount = getChildCount();
        View view;
        for (int i=0;i<childCount;i++){
            view = getChildAt(i);
            if(view == emptyView || view == errorView || view == loadingView){
                view.setVisibility(INVISIBLE);
            }else {
                view.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * 隐藏所有view
     */
    private void hideAllViews(){
        int childCount = getChildCount();
        View view;
        for (int i=0;i<childCount;i++){
            view = getChildAt(i);
            view.setVisibility(INVISIBLE);
        }
    }

    public void setErrorViewClickListener(OnClickListener listener){
        errorView.setOnClickListener(listener);
    }

    public View getLoadingView() {
        return loadingView;
    }

    public void setLoadingView(View loadingView) {
        this.loadingView = loadingView;
    }

    public View getErrorView() {
        return errorView;
    }

    public void setErrorView(View errorView) {
        this.errorView = errorView;
    }

    public View getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
}
