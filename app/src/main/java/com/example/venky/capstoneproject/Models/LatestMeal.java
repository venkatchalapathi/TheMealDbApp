package com.example.venky.capstoneproject.Models;

import android.os.Parcel;

import com.example.venky.capstoneproject.Models.MealInfo;

import java.util.List;

/**
 * Created by VENKY on 12/18/2018.
 */

public class LatestMeal {
    private List<MealInfo> meals;

    public void setMeals(List<MealInfo> meals) {
        this.meals = meals;
    }

    public LatestMeal(List<MealInfo> meals) {
        this.meals = meals;
    }

    protected LatestMeal(Parcel in) {
    }

    public LatestMeal() {

    }


    public List<MealInfo> getMeals() {
        return meals;
    }

}

