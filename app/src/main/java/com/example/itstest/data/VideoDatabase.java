package com.example.itstest.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Video.class, FavouriteVideo.class}, version = 4, exportSchema = false)
public abstract class VideoDatabase extends RoomDatabase {
    private static VideoDatabase database;
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "videos.db";

    public static VideoDatabase getInstance(Context context){
        synchronized (LOCK){
            if (database == null){
                database = Room.databaseBuilder(context, VideoDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration().build();
            }
        }
        return database;
    }

    public abstract VideoDao getVideoDao();
}
