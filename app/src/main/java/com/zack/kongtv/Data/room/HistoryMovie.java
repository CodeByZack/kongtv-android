package com.zack.kongtv.Data.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.zack.kongtv.bean.MovieItem;

@Entity(indices = {@Index(value = {"movieId"},unique = true)})
public class HistoryMovie{
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private long movieId;

    private String movieImg;
    private String movieName;
    private String movieStatus;
    private String movieType;
    private String movieDirector;
    private String movieActor;
    private String movieYear;
    private String movieLang;
    private String movieDesc;
    private String movieJuji;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public String getMovieActor() {
        return movieActor;
    }

    public void setMovieActor(String movieActor) {
        this.movieActor = movieActor;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getMovieLang() {
        return movieLang;
    }

    public void setMovieLang(String movieLang) {
        this.movieLang = movieLang;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getMovieJuji() {
        return movieJuji;
    }

    public void setMovieJuji(String movieJuji) {
        this.movieJuji = movieJuji;
    }

    private String movieRecord;

    public String getMovieRecord() {
        return movieRecord;
    }

    public void setMovieRecord(String movieRecord) {
        this.movieRecord = movieRecord;
    }

    public String getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(String movieStatus) {
        this.movieStatus = movieStatus;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getMovieImg() {
        return movieImg;
    }

    public void setMovieImg(String movieImg) {
        this.movieImg = movieImg;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
