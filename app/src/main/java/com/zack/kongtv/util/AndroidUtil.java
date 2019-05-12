package com.zack.kongtv.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zack.kongtv.App;
import com.zack.kongtv.Const;
import com.zack.kongtv.Data.Config;
import com.zackdk.Utils.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static List<Config> parseJSON2Map(String json) {

        Gson gson = new Gson();
        List<Config> configs = gson.fromJson(json, new TypeToken<List<Config>>() {
        }.getType());
        Log.d("Tag", "parseJSON2Map: "+json);

        return configs;
    }
    public static int count2(String srcStr, String findStr) {
        int count = 0;
        Pattern pattern = Pattern.compile(findStr);// 通过静态方法compile(String regex)方法来创建,将给定的正则表达式编译并赋予给Pattern类
        Matcher matcher = pattern.matcher(srcStr);//
        while (matcher.find()) {// boolean find() 对字符串进行匹配,匹配到的字符串可以在任何位置
            count++;
        }
        return count;
    }


}
