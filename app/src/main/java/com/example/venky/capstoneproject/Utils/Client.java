package com.example.venky.capstoneproject.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by VENKY on 12/18/2018.
 */

public class Client {
    public static Retrofit getClient(){

        return new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

