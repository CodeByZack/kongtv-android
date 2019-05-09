package com.zack.kongtv.Data.Instance;

import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.Cms_movie;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;

import java.util.List;

public interface GetDataInterface {
    List<Cms_movie> getHomeData();
    List<Cms_movie> getCategoryData(String url, int page);
    List<Cms_movie> getRealMovieDetail(String url);
    String getRealPlayUrl(String url);
    List<Cms_movie> search(String text, int page);

}
