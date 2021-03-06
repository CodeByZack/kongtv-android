package com.zack.kongtv.activities.MovieList;

import com.zack.kongtv.Data.room.HistoryMovie;
import com.zack.kongtv.bean.MovieItem;
import com.zackdk.mvp.v.IView;

import java.util.List;

public interface IMovieListView extends IView {
    public void updateView(List<MovieItem> data);
    public void setTitle(String title);
    public void clear();
}
