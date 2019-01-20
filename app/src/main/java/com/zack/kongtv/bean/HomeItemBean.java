package com.zack.kongtv.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zack on 2018/5/30.
 */

public class HomeItemBean {
    private ArrayList<Cms_movie> movieDetailBeans;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Cms_movie> getMovieDetailBeans() {
        return movieDetailBeans;
    }

    public void setMovieDetailBeans(ArrayList<Cms_movie> movieDetailBeans) {
        this.movieDetailBeans = movieDetailBeans;
    }
}
