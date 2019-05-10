package com.zack.kongtv.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.zack.kongtv.App;
import com.zack.kongtv.Const;
import com.zackdk.Utils.LogUtil;
import com.zackdk.Utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AndroidUtil {
    public static void copy(Context context,String copy){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copy);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        //Toast.makeText(context,"已复制："+copy,Toast.LENGTH_SHORT).show();
    }

    public static String getAlipayText(){
        int length = Const.AlipayTextArr.length;
        int x=(int)(Math.random()*length);
        if(x<length){
            LogUtil.d(Const.AlipayTextArr[x]);
            return Const.AlipayTextArr[x];
        }else{
            return "";
        }
    }
    public static void openAlipay(Context context){
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone");
        context.startActivity(intent);
    }

    public static String getJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assets = App.getContext().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assets.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<Map<String, Object>> parseJSON2Map(String json) {
        Log.d("Tag", "parseJSON2Map: "+json);
        List<Map<String,Object>> list = new LinkedList<>();
        JSONArray jsonArray = null;
        try {
           jsonArray = new JSONArray(json);
           Log.d("Tag", "parseJSON2Map: "+json);
            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String,Object> map = new LinkedHashMap<>();
                



                map.put(jsonObject.getString("name"),jsonObject);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }
}
