package com.zack.kongtv.activities.MovieDetail;

import com.zack.kongtv.bean.MovieDetailBean;
import com.zackdk.mvp.v.IView;

public interface IMovieDetailView extends IView{
    public void updateView(MovieDetailBean data);
}
