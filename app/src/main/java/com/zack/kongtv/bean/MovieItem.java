package com.zack.kongtv.bean;

public class MovieItem {
    private String targetUrl;

    private String movieImg;
    private String movieName;
    private String movieStatus;
    private String movieType;
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

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
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
