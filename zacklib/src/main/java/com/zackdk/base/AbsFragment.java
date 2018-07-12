package com.zackdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zackdk.Utils.LogUtil;


/**
 * 基本的Fragment。最好Fragment都要继承于此类
 * Created by du on 2017/9/19.
 */
public abstract class AbsFragment extends Fragment {

    private boolean mLoaded = false;//是否已经载入
    protected Toolbar mToolbar;
    protected View mRootView;
    protected AbsActivity mActivity;
    protected boolean printLife = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(printLife){
            LogUtil.d("-----------onCreateView--------------");
        }
        mActivity = (AbsActivity) getActivity();
        int vid = setView();
        Context context = container != null ? container.getContext() : null;

        if (mRootView == null && vid != 0  && context != null) {
            mRootView = createRootView(LayoutInflater.from(context).inflate(vid, container, false));
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(printLife){
            LogUtil.d("-----------onViewCreated--------------");
        }
        if (!mLoaded && mRootView != null) {
            mLoaded = true;
            LogUtil.d("-----------initBasic--------------");
            initBasic(savedInstanceState);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(printLife){
            LogUtil.d("-----------onattach--------------");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(printLife){
            LogUtil.d("-----------oncreate--------------");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(printLife){
            LogUtil.d("-----------onActivityCreated--------------");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(printLife){
            LogUtil.d("-----------onStart--------------");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(printLife){
            LogUtil.d("-----------onResume--------------");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(printLife){
            LogUtil.d("-----------onPause--------------");
        }
    }

    @Override
    public void onStop() {
        if(printLife){
            LogUtil.d("-----------onStop--------------");
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if(printLife){
            LogUtil.d("-----------onDestroyView--------------");
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if(printLife){
            LogUtil.d("-----------onDestroy--------------");
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        if(printLife){
            LogUtil.d("-----------onDetach--------------");
        }
        super.onDetach();
    }

    public View createRootView(View view) {
        LinearLayout layout = new LinearLayout(getActivity());
        mToolbar = initToolbar(layout);
        if (mToolbar != null) {
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(mToolbar);
            layout.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                    , LinearLayout.LayoutParams.MATCH_PARENT));
            return layout;
        }
        return view;
    }

    public Toolbar initToolbar(ViewGroup parent) {
        return null;
    }

    public abstract int setView();

    public abstract void initBasic(Bundle savedInstanceState);

    protected <T extends View> T findViewById(@IdRes int resId) {
        return (T) mRootView.findViewById(resId);
    }
}
