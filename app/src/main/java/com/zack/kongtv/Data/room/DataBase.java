package com.zack.kongtv.Data.room;


import android.content.Context;

import com.zack.kongtv.App;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HistoryMovie.class,CollectMovie.class}, version = 3,exportSchema = false)
public abstract class DataBase extends RoomDatabase {
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
