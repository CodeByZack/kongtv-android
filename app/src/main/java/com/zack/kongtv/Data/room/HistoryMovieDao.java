package com.zack.kongtv.Data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.graphics.Movie;

import com.zack.kongtv.bean.MovieItem;

import java.util.List;

@Dao
public interface HistoryMovieDao {
    /**
     * 查询所有
     *
     * @return
     */
    @Query("SELECT * FROM HistoryMovie")
    List<HistoryMovie> getAllHistory();

    /**
     * 根据指定字段查询
     *
     * @return
     */
    @Query("SELECT * FROM HistoryMovie WHERE  targetUrl= :url")
    HistoryMovie findByTargetUrl(String url);

    /**
     * 项数据库添加数据
     *
     * @param movie
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HistoryMovie> movie);

    /**
     * 项数据库添加数据
     *
     * @param movie
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistoryMovie movie);

    /**
     * 修改数据
     *
     * @param movie
     */
    @Update()
    void update(HistoryMovie movie);

    /**
     * 删除数据
     *
     * @param movie
     */
    @Delete()
    void delete(HistoryMovie movie);

    /**
     * 删除数据
     *
     * @param movies
     */
    @Delete()
    void delete(List<HistoryMovie> movies);
}
