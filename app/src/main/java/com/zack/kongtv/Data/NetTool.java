package com.zack.kongtv.Data;

import com.zackdk.NetTool.RetrofitFactory;

import java.util.HashMap;

public class NetTool {
    public static HashMap<String,String> getParamMap(String year,String area,String classname,int page) {
        HashMap<String,String> map = new HashMap<>();
        if(year != null){
            map.put("year", year);
        }
        if(area != null){
            map.put("area",area);
        }
        if(classname != null){
            map.put("class",classname);
        }
        if(page != 0){
            map.put("page", String.valueOf(page));
        }
        return map;
    }
    public static HashMap<String,String> getEpisodeMap() {
        HashMap<String,String> map = new HashMap<>();
        map.put("ac","detail");
        map.put("t","2");
        return map;
    }
    public static HashMap<String,String> getAnimeMap() {
        HashMap<String,String> map = new HashMap<>();
        map.put("ac","detail");
        map.put("t","3");
        return map;
    }
    public static HashMap<String,String> getVarietyMap() {
        HashMap<String,String> map = new HashMap<>();
        map.put("ac","detail");
        map.put("t","4");
        return map;
    }





    private static  Api api;
    public static Api getInstance() {
        if(api==null){
            synchronized (NetTool.class){
                if(api == null){
                    api = RetrofitFactory.getInstance().getRetrofit().create(Api.class);
                }
            }
        }
        return api;
    }
}
