package com.zack.kongtv.Data.Instance;

import com.zack.kongtv.bean.CategoryDataBean;
import com.zack.kongtv.bean.HomeDataBean;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;

public interface GetDataInterface {
    HomeDataBean getHomeData();
    CategoryDataBean getCategoryData(String url, int page);
    MovieDetailBean getRealMovieDetail(String url);
    String getRealPlayUrl(String url);
    SearchResultBean search(String text, int page);

    String getBaseUrl();
    String getMovieUrl();
    String getEpisodeUrl();
    String getAnimeUrl();
    String getVarietyUrl();
    String getSearchUrl();
    String getName();
}
