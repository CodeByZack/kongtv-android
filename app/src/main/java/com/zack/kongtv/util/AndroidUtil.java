package com.zack.kongtv.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.zack.kongtv.Const;
import com.zack.kongtv.Data.room.CollectMovie;
import com.zack.kongtv.Data.room.HistoryMovie;
import com.zack.kongtv.bean.Cms_movie;
import com.zackdk.Utils.LogUtil;
import com.zackdk.Utils.ToastUtil;

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

    public static CollectMovie transferCollect(Cms_movie movie){
        CollectMovie collectMovie = new CollectMovie();

        collectMovie.setMovieId(movie.getVodId());
        collectMovie.setMovieLang(movie.getVodLang());
        collectMovie.setMovieActor(movie.getVodActor());
        collectMovie.setMovieDesc(movie.getVodBlurb());
        collectMovie.setMovieDirector(movie.getVodDirector());
        collectMovie.setMovieImg(movie.getVodPic());
        collectMovie.setMovieJuji(movie.getVodPlayUrl());
        collectMovie.setMovieName(movie.getVodName());
        collectMovie.setMovieStatus(movie.getVodRemarks());
        collectMovie.setMovieYear(movie.getVodYear());
        collectMovie.setMovieType(movie.getVodClass());

        return collectMovie;
    }

    public static HistoryMovie transferHistory(Cms_movie movie,String record){
        HistoryMovie historyMovie = new HistoryMovie();

        historyMovie.setMovieId(movie.getVodId());
        historyMovie.setMovieLang(movie.getVodLang());
        historyMovie.setMovieActor(movie.getVodActor());
        historyMovie.setMovieDesc(movie.getVodBlurb());
        historyMovie.setMovieDirector(movie.getVodDirector());
        historyMovie.setMovieImg(movie.getVodPic());
        historyMovie.setMovieJuji(movie.getVodPlayUrl());
        historyMovie.setMovieName(movie.getVodName());
        historyMovie.setMovieStatus(movie.getVodRemarks());
        historyMovie.setMovieYear(movie.getVodYear());
        historyMovie.setMovieType(movie.getVodClass());
        historyMovie.setMovieRecord(record);
        return historyMovie;
    }

    public static Cms_movie transferFromCollect(CollectMovie collectMovie){
        Cms_movie movie = new Cms_movie();
        movie.setVodId(collectMovie.getMovieId());
        movie.setVodLang(collectMovie.getMovieLang());
        movie.setVodActor(collectMovie.getMovieActor());
        movie.setVodBlurb(collectMovie.getMovieDesc());
        movie.setVodDirector(collectMovie.getMovieDirector());
        movie.setVodPic(collectMovie.getMovieImg());
        movie.setVodPlayUrl(collectMovie.getMovieJuji());
        movie.setVodName(collectMovie.getMovieName());
        movie.setVodRemarks(collectMovie.getMovieStatus());
        movie.setVodYear(collectMovie.getMovieYear());
        movie.setVodClass(collectMovie.getMovieType());
        return movie;
    }
    public static Cms_movie transferFromHistory(HistoryMovie historyMovie){
        Cms_movie movie = new Cms_movie();
        movie.setVodId(historyMovie.getMovieId());
        movie.setVodLang(historyMovie.getMovieLang());
        movie.setVodActor(historyMovie.getMovieActor());
        movie.setVodBlurb(historyMovie.getMovieDesc());
        movie.setVodDirector(historyMovie.getMovieDirector());
        movie.setVodPic(historyMovie.getMovieImg());
        movie.setVodPlayUrl(historyMovie.getMovieJuji());
        movie.setVodName(historyMovie.getMovieName());
        movie.setVodRemarks(historyMovie.getMovieStatus());
        movie.setVodYear(historyMovie.getMovieYear());
        movie.setVodClass(historyMovie.getMovieType());
        movie.setRecord(historyMovie.getMovieRecord());
        return movie;
    }
}
