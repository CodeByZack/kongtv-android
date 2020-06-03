package com.zack.kongtv.Data.room;



import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CollectMovieDao {
    /**
     * 查询所有
     *
     * @return
     */
    @Query("SELECT * FROM CollectMovie ORDER BY id DESC")
    List<CollectMovie> getAllCollect();

    @Query("SELECT * FROM CollectMovie WHERE movieId= :id")
    CollectMovie getCollectById(long id);



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
