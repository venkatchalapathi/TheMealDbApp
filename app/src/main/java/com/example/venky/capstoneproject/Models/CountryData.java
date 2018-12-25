
package com.example.venky.capstoneproject.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryData {

    @SerializedName("meals")
    @Expose
    private List<Meal> meals = null;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

}
