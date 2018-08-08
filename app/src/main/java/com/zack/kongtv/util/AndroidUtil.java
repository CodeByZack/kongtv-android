package com.zack.kongtv.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.zack.kongtv.Const;
import com.zackdk.Utils.ToastUtil;

public class AndroidUtil {
    public static void copy(Context context,String copy){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copy);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        Toast.makeText(context,"已复制："+copy,Toast.LENGTH_SHORT).show();
    }

    public static String getAlipayText(){
        int length = Const.AlipayTextArr.length;
        int x=(int)(Math.random()*length);
        if(x<length){
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

}
