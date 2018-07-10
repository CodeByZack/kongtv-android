package com.zack.kongtv.Data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CollectMovieDao {
    /**
     * 查询所有
     *
     * @return
     */
    @Query("SELECT * FROM CollectMovie")
    List<CollectMovie> getAllCollect();

    /**
     * 根据指定字段查询
     *
     * @return
     */
    @Query("SELECT * FROM CollectMovie WHERE  targetUrl= :url")
    CollectMovie findByTargetUrl(String url);

    /**
     * 项数据库添加数据
     *
     * @param movie
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CollectMovie> movie);

    /**
     * 项数据库添加数据
     *
     * @param movie
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CollectMovie movie);

    /**
     * 修改数据
     *
     * @param movie
     */
    @Update()
    void update(CollectMovie movie);

    /**
     * 删除数据
     *
     * @param movie
     */
    @Delete()
    void delete(CollectMovie movie);

    /**
     * 删除数据
     *
     * @param movies
     */
    @Delete()
    void delete(List<CollectMovie> movies);
}
