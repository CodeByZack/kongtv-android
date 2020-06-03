package com.zack.kongtv.Data.room;


import android.graphics.Movie;

import com.zack.kongtv.bean.MovieItem;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface HistoryMovieDao {
    /**
     * 查询所有
     *
     * @return
     */
    @Query("SELECT * FROM HistoryMovie ORDER BY id DESC")
    List<HistoryMovie> getAllHistory();



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
