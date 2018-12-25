package com.example.venky.capstoneproject.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.venky.capstoneproject.Models.FavInfo;
import com.example.venky.capstoneproject.Models.MealInfo;

import java.util.List;

/**
 * Created by VENKY on 12/18/2018.
 */


@Dao
public interface MealDAO {
    @Query("SELECT * FROM meal_table")
    LiveData<List<MealInfo>> getAllFavs();

    @Query("SELECT * FROM meal_table WHERE idMeal = :id")
    public boolean isCheckFav(String id);

    @Insert
    public void addToFavs(MealInfo entity);

    @Delete
    public void delFromFav(MealInfo entity);
}

