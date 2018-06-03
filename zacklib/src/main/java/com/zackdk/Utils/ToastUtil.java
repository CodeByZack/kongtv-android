package com.zackdk.Utils;

import android.widget.Toast;

/**
 * Created by Administrator on 2018/2/28.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(String text){
        if(toast==null){
            toast = Toast.makeText(Utils.application, text,Toast.LENGTH_SHORT);
        }else {
            toast.setText(text);//如果不为空，则直接改变当前toast的文本
        }
        toast.show();
    }
}
