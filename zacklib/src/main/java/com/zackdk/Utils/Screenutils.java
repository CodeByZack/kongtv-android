package com.zackdk.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Zackv on 2018/5/2.
 */

public class Screenutils {
    public Screenutils() {
    }

    public static int dp2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
}
