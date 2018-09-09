package com.zack.kongtv.Data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.zack.kongtv.App;

@Database(entities = {HistoryMovie.class,CollectMovie.class}, version = 2,exportSchema = false)
public abstract class DataBase extends RoomDatabase{
    private static DataBase INSTANCE;
    public abstract HistoryMovieDao historyMovieDao();
    public abstract CollectMovieDao collectMovieDao();

    public static DataBase getInstance(){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(App.getContext(),DataBase.class,"movie.db")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
