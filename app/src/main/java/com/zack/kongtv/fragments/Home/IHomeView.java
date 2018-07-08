package com.zack.kongtv.fragments.Home;

import com.zack.kongtv.bean.HomeDataBean;
import com.zackdk.mvp.v.IView;

public interface IHomeView extends IView {
    public void updateView(HomeDataBean data);

    public void setRefresh(boolean refresh);
}
