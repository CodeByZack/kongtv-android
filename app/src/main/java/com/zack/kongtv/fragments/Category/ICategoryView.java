package com.zack.kongtv.fragments.Category;

import com.zack.kongtv.bean.CategoryDataBean;
import com.zackdk.mvp.v.IView;

import java.util.Locale;

public interface ICategoryView extends IView {
    void updateView(CategoryDataBean data);
    void loadMoreComplete();
    void loadMoreEnd();
}
