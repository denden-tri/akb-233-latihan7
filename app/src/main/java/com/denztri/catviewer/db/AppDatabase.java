package com.denztri.catviewer.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.denztri.catviewer.models.CatModels;

@Database(entities = CatModels.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CatDao      catDao();

    private static AppDatabase  INSTANCE;

    public static AppDatabase getDbInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "catviewer")
                    .build();
        }
        return INSTANCE;
    }
}
