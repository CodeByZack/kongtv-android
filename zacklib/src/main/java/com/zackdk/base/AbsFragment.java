package com.zackdk.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


/**
 * 基本的Fragment。最好Fragment都要继承于此类
 * Created by du on 2017/9/19.
 */
public abstract class AbsFragment extends Fragment {

    private boolean mLoaded = false;//是否已经载入
    protected Toolbar mToolbar;
    protected View mRootView;
    protected AbsActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        if (!mLoaded && mRootView != null) {
            mLoaded = true;
            initBasic(savedInstanceState);
        }
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
