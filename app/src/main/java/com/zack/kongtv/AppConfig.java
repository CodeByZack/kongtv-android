package com.zack.kongtv;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.zackdk.Utils.SPUtil;

import java.util.LinkedList;
import java.util.List;

public class AppConfig {
    private static final String baseUrlBABAYU = "http://www.yunbtv.com";
    private static final String EpisodeUrlBABAYU = baseUrlBABAYU+"/search.php?page=PAGE&searchtype=5&tid=2";
    private static final String MovieUrlBABAYU = baseUrlBABAYU+"/search.php?page=PAGE&searchtype=5&tid=1";
    private static final String AnimeUrlBABAYU = baseUrlBABAYU+"/search.php?page=PAGE&searchtype=5&tid=4";
    private static final String VarietyUrlBABAYU = baseUrlBABAYU+"/search.php?page=PAGE&searchtype=5&tid=3";
    private static final String SearchUrlBABAYU = baseUrlBABAYU+"/vod-search-wd-TEMP-p-PAGE.html";

    private static final String baseUrlYIMIMAO = "https://m.yimimao.com";
    private static final String MovieUrlYIMIMAO = baseUrlYIMIMAO+"/dy/index_1_______1.html";
    private static final String EpisodeUrlYIMIMAO = baseUrlYIMIMAO+"/dsj/index_1_______1.html";
    private static final String AnimeUrlYIMIMAO = baseUrlYIMIMAO+"/dm/index_1_______1.html";
    private static final String VarietyUrlYIMIMAO = baseUrlYIMIMAO+"/arts/index_1_______1.html";
    private static final String SearchUrlYIMIMAO = baseUrlYIMIMAO+"/vod-search-wd-TEMP-p-PAGE.html";

    private static final String baseUrl_4KWU = "http://m.kkkkmao.com";
    private static final String MovieUrl_4KWU = baseUrl_4KWU+"/movie/index_1_______1.html";
    private static final String EpisodeUrl_4KWU = baseUrl_4KWU+"/tv/index_1_______1.html";
    private static final String AnimeUrl_4KWU = baseUrl_4KWU+"/Animation/index_1_______1.html";
    private static final String VarietyUrl_4KWU = baseUrl_4KWU+"/Arts/index_1_______1.html";
    private static final String SearchUrl_4KWU = baseUrl_4KWU+"/vod-search-wd-TEMP-p-PAGE.html";

    private static final String baseUrlKANKANWU = "https://m.kankanwu.com";
    private static final String MovieUrlKANKANWU = baseUrlKANKANWU+"/dy/index_1_______1.html";
    private static final String EpisodeUrlKANKANWU = baseUrlKANKANWU+"/dsj/index_1_______1.html";
    private static final String AnimeUrlKANKANWU = baseUrlKANKANWU+"/Animation/index_1_______1.html";
    private static final String VarietyUrlKANKANWU = baseUrlKANKANWU+"/Arts/index_1_______1.html";
    private static final String SearchUrlKANKANWU = baseUrlKANKANWU+"/vod-search-wd-TEMP-p-PAGE.html";

    private static final String baseUrlPIPIGUI = "https://m.pipigui.cc";
    private static final String MovieUrlPIPIGUI = baseUrlPIPIGUI+"/dianying/index_1_______1.html";
    private static final String EpisodeUrlPIPIGUI = baseUrlPIPIGUI+"/tv/index_1_______1.html";
    private static final String AnimeUrlPIPIGUI = baseUrlPIPIGUI+"/dongman/index_1_______1.html";
    private static final String VarietyUrlPIPIGUI = baseUrlPIPIGUI+"/zongyi/index_1_______1.html";
    private static final String SearchUrlPIPIGUI = baseUrlPIPIGUI+"/vod-search-wd-TEMP-p-PAGE.html";

    private static final String XIANLU = "XIANLU";

    public static final int BABAYU = 1;
    public static final int YIMIMAO = 2;
    public static final int _4KWU = 3;
    public static final int KANKANWU = 4;
    public static final int PIPIGUI = 5;

    private static int defaultXIANLU = YIMIMAO;

    //统一提供URL
    public static  String baseUrl = "";
    public static  String MovieUrl = "";
    public static  String EpisodeUrl = "";
    public static  String AnimeUrl = "";
    public static  String VarietyUrl = "";
    public static  String SearchUrl = "";

    public static void initUrl(Context context){
        int xianluType = (int) SPUtil.getData(context,XIANLU,defaultXIANLU);
        defaultXIANLU = xianluType;
        switch (xianluType){
            case BABAYU:
                baseUrl = baseUrlBABAYU;
                MovieUrl = MovieUrlBABAYU;
                EpisodeUrl = EpisodeUrlBABAYU;
                AnimeUrl = AnimeUrlBABAYU;
                VarietyUrl = VarietyUrlBABAYU;
                SearchUrl = SearchUrlBABAYU;
                break;
            case YIMIMAO:
                baseUrl = baseUrlYIMIMAO;
                MovieUrl = MovieUrlYIMIMAO;
                EpisodeUrl = EpisodeUrlYIMIMAO;
                AnimeUrl = AnimeUrlYIMIMAO;
                VarietyUrl = VarietyUrlYIMIMAO;
                SearchUrl = SearchUrlYIMIMAO;
                break;
            case _4KWU:
                baseUrl = baseUrl_4KWU;
                MovieUrl = MovieUrl_4KWU;
                EpisodeUrl = EpisodeUrl_4KWU;
                AnimeUrl = AnimeUrl_4KWU;
                VarietyUrl = VarietyUrl_4KWU;
                SearchUrl = SearchUrl_4KWU;
                break;
            case KANKANWU:
                baseUrl = baseUrlKANKANWU;
                MovieUrl = MovieUrlKANKANWU;
                EpisodeUrl = EpisodeUrlKANKANWU;
                AnimeUrl = AnimeUrlKANKANWU;
                VarietyUrl = VarietyUrlKANKANWU;
                SearchUrl = SearchUrlKANKANWU;
                break;
            case PIPIGUI:
                baseUrl = baseUrlPIPIGUI;
                MovieUrl = MovieUrlPIPIGUI;
                EpisodeUrl = EpisodeUrlPIPIGUI;
                AnimeUrl = AnimeUrlPIPIGUI;
                VarietyUrl = VarietyUrlPIPIGUI;
                SearchUrl = SearchUrlPIPIGUI;
                break;
        }
    }

    public static int getDefaultXIANLU() {
        return defaultXIANLU;
    }

    public static void setDefaultXIANLU(int defaultXIANLU,Context context) {
        AppConfig.defaultXIANLU = defaultXIANLU;
        SPUtil.saveDate(context,XIANLU,defaultXIANLU);
        initUrl(context);
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static List<String> getXianLuList(){
        List<String> list = new LinkedList<>();
        //list.add("线路"+String.valueOf(BABAYU));
        list.add("线路"+String.valueOf(YIMIMAO));
        //list.add("线路"+String.valueOf(_4KWU));
        //list.add("线路"+String.valueOf(KANKANWU));
        list.add("线路"+String.valueOf(PIPIGUI));
        return list;
    }

    public static String getNowXianLu(){
        String temp = "";
        switch (defaultXIANLU){
            case BABAYU:
                temp+="线路1";
                break;
            case YIMIMAO:
                temp+="线路2";
                break;
            case _4KWU:
                temp+="线路3";
                break;
            case KANKANWU:
                temp+="线路4";
                break;
            case PIPIGUI:
                temp+="线路5";
                break;
        }
        return temp;
    }
}
