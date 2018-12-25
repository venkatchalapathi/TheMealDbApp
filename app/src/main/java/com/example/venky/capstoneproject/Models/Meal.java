
package com.example.venky.capstoneproject.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meal {

    @SerializedName("strMeal")
    @Expose
    private String strMeal;
    @SerializedName("strMealThumb")
    @Expose
    private String strMealThumb;
    @SerializedName("idMeal")
    @Expose
    private String idMeal;

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

}
