package com.zack.kongtv.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zack on 2018/5/30.
 */

public class HomeItemBean {
    private ArrayList<MovieDetailBean> movieDetailBeans;
    private int type;

    public ArrayList<MovieDetailBean> getMovieDetailBeans() {
        return movieDetailBeans;
    }

    public void setMovieDetailBeans(ArrayList<MovieDetailBean> movieDetailBeans) {
        this.movieDetailBeans = movieDetailBeans;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
