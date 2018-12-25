package com.example.venky.capstoneproject.Utils;

import com.example.venky.capstoneproject.Models.LatestMeal;
import com.example.venky.capstoneproject.Models.Category;
import com.example.venky.capstoneproject.Models.CountryData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by VENKY on 12/18/2018.
 */

public interface Api {
    public String BASE_URL="https://www.themealdb.com/api/json/v1/1/";

    @GET("randomselection.php")
    Call<LatestMeal> getRandomMeal();

    @GET("lookup.php")
    Call<LatestMeal> getMealDetails(@Query("i") int mealId);

    @GET("filter.php")
    Call<CountryData> getCountry(@Query("a") String country);

    @GET("filter.php")
    Call<CountryData> getCategory(@Query("c") String category);

    @GET("categories.php")
    Call<ArrayList<Category>> getCategores();

    @GET("latest.php")
    Call<LatestMeal> getLatestMeal();
}

