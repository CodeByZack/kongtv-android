package com.zack.kongtv.activities.MovieList;

import com.zack.kongtv.Const;
import com.zack.kongtv.Data.room.CollectMovie;
import com.zack.kongtv.Data.room.DataBase;
import com.zack.kongtv.Data.room.HistoryMovie;
import com.zack.kongtv.bean.MovieItem;
import com.zackdk.mvp.p.BasePresenter;
import com.zackdk.mvp.v.IView;

import java.util.LinkedList;
import java.util.List;

public class MovieListPresenter<V extends IMovieListView> extends BasePresenter<V> {
    private int mode;

    public void requestData() {
        switch (mode){
            case Const.History:
                getDataHistory();
                break;
            case Const.Collect:
                getDataCollect();
                break;
        }

    }

    private void getDataHistory() {
        getView().setTitle("观看记录");
        List<HistoryMovie> data = DataBase.getInstance().historyMovieDao().getAllHistory();
        List<MovieItem> list = new LinkedList<>();
        for (HistoryMovie historyMovie:data) {
            MovieItem d = new MovieItem();
            d.setMovieImg(historyMovie.getMovieImg());
            d.setTargetUrl(historyMovie.getTargetUrl());
            d.setMovieName(historyMovie.getMovieName());
            d.setMovieStatus(historyMovie.getMovieStatus());
            d.setMovieType(historyMovie.getMovieType());
            list.add(d);
        }

        getView().updateView(list);
    }

    public void getDataCollect() {
        getView().setTitle("我的收藏");
        List<CollectMovie> data = DataBase.getInstance().collectMovieDao().getAllCollect();
        List<MovieItem> list = new LinkedList<>();
        for (CollectMovie historyMovie:data) {
            MovieItem d = new MovieItem();
            d.setMovieImg(historyMovie.getMovieImg());
            d.setTargetUrl(historyMovie.getTargetUrl());
            d.setMovieStatus(historyMovie.getMovieStatus());
            d.setMovieType(historyMovie.getMovieType());
            d.setMovieName(historyMovie.getMovieName());
            list.add(d);
        }
        getView().updateView(list);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }


    public void deleteAll() {
        switch (mode){
            case Const.History:
                DataBase.getInstance().historyMovieDao().delete(DataBase.getInstance().historyMovieDao().getAllHistory());
                break;
            case Const.Collect:
                DataBase.getInstance().collectMovieDao().delete(DataBase.getInstance().collectMovieDao().getAllCollect());
                break;
        }
        getView().clear();
    }
}
