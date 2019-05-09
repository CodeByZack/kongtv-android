package com.zack.kongtv.activities.MovieDetail;

import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zackdk.mvp.v.IView;

public interface IMovieDetailView extends IView{
    public void updateView(Cms_movie data);
    public void collect(boolean c);
    public void setRecord(String record);
}
