package com.zackdk.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zack on 2018/2/28.
 */

public class SPUtil {
    private static final String FILE_NAME = "default_sp_name";

    public static void saveDate(Context context , String key, Object object){
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }
        editor.commit();
    }

    public static void saveDate(Context context ,String saveFileName, String key, Object object){
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(saveFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }
        editor.commit();
    }

    public static Object getData(Context context , String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }
        return null;
    }

    public static Object getData(Context context , String saveFileName ,String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(saveFileName, Context.MODE_PRIVATE);
        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }
        return null;
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }

    public static void clear(Context context, String saveFileName) {
        SharedPreferences sp = context.getSharedPreferences(saveFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }

    public static void clearValue(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clearValue(Context context,String saveFileName,String key) {
        SharedPreferences sp = context.getSharedPreferences(saveFileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
}
