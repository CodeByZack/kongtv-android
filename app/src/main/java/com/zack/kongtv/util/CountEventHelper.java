package com.zack.kongtv.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.zack.kongtv.bean.MovieDetailBean;

import java.util.HashMap;


public class CountEventHelper {

    /**
     * 统计电影搜索
     */
    public static void countMovieSearch(Context context, String keyword) {
        HashMap<String, String> map = new HashMap<>();
        map.put("keyword", keyword);
        MobclickAgent.onEvent(context, "movie_search", map);
    }

    /**
     * 统计电影详情观看
     */
    public static void countMovieDetail(Context context, String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("movie_name", name);
        MobclickAgent.onEvent(context, "movie_detail", map);
    }

    /**
     * 统计电影观看
     */
    public static void countMovieWatch(Context context, String targetUrl, String movieName) {
        HashMap<String, String> map = new HashMap<>();
        map.put("target_url", targetUrl);
        map.put("movie_name", movieName);
        MobclickAgent.onEvent(context, "movie_watch", map);
    }

    /**
     * 统计捐助点击次数
     */
    public static void countAlipay(Context context) {
        MobclickAgent.onEvent(context, "Alipay_click");
    }

    /**
     * 统计加群点击次数
     */
    public static void countJoinQQ(Context context) {
        MobclickAgent.onEvent(context, "join_qq");
    }

    /**
     * 统计邮件反馈点击次数
     */
    public static void countOpenEmail(Context context) {
        MobclickAgent.onEvent(context, "open_email");
    }
}
