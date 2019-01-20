package com.zack.kongtv.activities.MovieList;

import com.zack.kongtv.Const;
import com.zack.kongtv.Data.room.CollectMovie;
import com.zack.kongtv.Data.room.DataBase;
import com.zack.kongtv.Data.room.HistoryMovie;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.MovieItem;
import com.zack.kongtv.util.AndroidUtil;
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
        List<Cms_movie> list = new LinkedList<>();
        for (HistoryMovie historyMovie:data) {
            list.add(AndroidUtil.transferFromHistory(historyMovie));
        }

        getView().updateView(list);
    }

    public void getDataCollect() {
        getView().setTitle("我的收藏");
        List<CollectMovie> data = DataBase.getInstance().collectMovieDao().getAllCollect();
        List<Cms_movie> list = new LinkedList<>();
        for (CollectMovie collectMovie:data) {
            list.add(AndroidUtil.transferFromCollect(collectMovie));
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
