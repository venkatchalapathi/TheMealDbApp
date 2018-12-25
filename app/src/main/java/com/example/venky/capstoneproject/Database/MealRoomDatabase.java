package com.example.venky.capstoneproject.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.venky.capstoneproject.Models.FavInfo;
import com.example.venky.capstoneproject.Models.MealInfo;

/**
 * Created by VENKY on 12/18/2018.
 */
@Database(entities = {MealInfo.class},version = 1,exportSchema = false)
public abstract class MealRoomDatabase extends RoomDatabase {
    private static final String DATABASE = "meals_database";
    public abstract MealDAO mealDAO();

    private static volatile MealRoomDatabase INSTANCE;

    static MealRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MealRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MealRoomDatabase.class, DATABASE)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;

    }
}

