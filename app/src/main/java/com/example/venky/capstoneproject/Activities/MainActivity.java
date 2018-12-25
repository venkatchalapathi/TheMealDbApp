package com.example.venky.capstoneproject.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;


import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.venky.capstoneproject.Adapters.CountryAndCategoryAdapter;
import com.example.venky.capstoneproject.Adapters.LatestAdapter;
import com.example.venky.capstoneproject.Database.MealViewModel;
import com.example.venky.capstoneproject.Utils.Api;
import com.example.venky.capstoneproject.Utils.Client;

import com.example.venky.capstoneproject.Models.CountryData;
import com.example.venky.capstoneproject.Models.LatestMeal;
import com.example.venky.capstoneproject.Models.Meal;
import com.example.venky.capstoneproject.Models.MealInfo;
import com.example.venky.capstoneproject.Database.MealRoomDatabase;
import com.example.venky.capstoneproject.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private MealRoomDatabase database;
    private ArrayList<MealInfo> favList;

    ProgressDialog dialog;
    private ActionBar actionBar;

    private SharedPreferences sharedPreferences;
    private static String SORT_ORDER = "sort_order";
    private static String BROWSE_KEY = "brows_by";
    public static String RESULT = "result";

    private static final int LATEST = 0;
    private static final int RANDOM = 1;
    private static final int FAV = 2;
    private static final int BY_COUNTRY = 3;
    private static final int BY_NAME = 4;
    private FirebaseAuth mAuth;
    private SharedPreferences.Editor editor;
    private RecyclerView mRecyclerview;
    private MealViewModel viewModel;
    TextView no_meals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.ads_app_id));
        dialog = new ProgressDialog(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        no_meals = findViewById(R.id.no_meals_texview);
        AdView mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
        //showProgress();
        viewModel = ViewModelProviders.of(this).get(MealViewModel.class);
        mRecyclerview = findViewById(R.id.recyclerview_id);
        favList = new ArrayList<>();

        if (mAuth.getCurrentUser() != null){
            mRecyclerview.setLayoutManager(
                    new GridLayoutManager(MainActivity.this, 2));
            sharedPreferences = getSharedPreferences(SORT_ORDER, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.apply();
            if (isNetworkAvailable()) {
                doCall(sharedPreferences.getInt(SORT_ORDER, 0));
            } else {
                showAlert();
            }
        }else {
            startActivity(new Intent(this, LoginActivity.class));
        }


    }

    private void doCall(int anInt) {
        switch (anInt) {
            case LATEST:
                callLatest();
                break;
            case RANDOM:
                callRandom();
                break;
            case FAV:
                callFav();
                break;
            case BY_COUNTRY:
                callCountryData(sharedPreferences.getString(BROWSE_KEY,""));
                break;
            case BY_NAME:
                callNameData(sharedPreferences.getString(BROWSE_KEY,""));
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.latest: {
                editor.putInt(SORT_ORDER, LATEST);
                editor.commit();
                callLatest();
                break;
            }
            case R.id.random: {
                editor.putInt(SORT_ORDER, RANDOM);
                editor.commit();
                callRandom();
                break;
            }
            case R.id.favourite: {
                editor.putInt(SORT_ORDER, FAV);
                editor.commit();
                callFav();
                break;
            }
            case R.id.country: {
                editor.putInt(SORT_ORDER, BY_COUNTRY);
                editor.commit();
                Intent intent = new Intent(this,BrowseByActivity.class);
                intent.putExtra(BrowseByActivity.INT_KEY,BY_COUNTRY);
                startActivityForResult(intent,3);
                break;

            }
            case R.id.name: {
                editor.putInt(SORT_ORDER, BY_NAME);
                editor.commit();
                Intent intent = new Intent(this,BrowseByActivity.class);
                intent.putExtra(BrowseByActivity.INT_KEY,BY_NAME);
                startActivityForResult(intent,3);
                break;
            }
            case R.id.signout:
                mAuth.signOut();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3){
            if(resultCode == Activity.RESULT_OK){

                if (sharedPreferences.getInt(SORT_ORDER,3) == BY_COUNTRY){
                    String res = data.getStringExtra(RESULT);
                    Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
                    editor.putString(BROWSE_KEY,res);
                    editor.commit();
                    callCountryData(res);
                }else if (sharedPreferences.getInt(SORT_ORDER,3) == BY_NAME){
                    String res = data.getStringExtra(RESULT);
                    Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
                    editor.putString(BROWSE_KEY,res);
                    editor.commit();
                    callNameData(res);
                }
            }
        }
    }

    private void callNameData(String result) {
        getSupportActionBar().setTitle(result+getString(R.string.items));

        showProgress();
        Api api = Client.getClient().create(Api.class);
        Call<CountryData> call = api.getCategory(result);
        call.enqueue(new Callback<CountryData>() {
            @Override
            public void onResponse(Call<CountryData> call, Response<CountryData> response) {
                CountryData list = response.body();
                ArrayList<Meal> arrayList = (ArrayList<Meal>) list.getMeals();
                setupBrowseRecyclerview(arrayList);
            }

            @Override
            public void onFailure(Call<CountryData> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callCountryData(String result) {
        getSupportActionBar().setTitle(result+getString(R.string.countryes_title));

        Api api = Client.getClient().create(Api.class);
        Call<CountryData> call = api.getCountry(result);
        call.enqueue(new Callback<CountryData>() {
            @Override
            public void onResponse(Call<CountryData> call, Response<CountryData> response) {
                CountryData list = response.body();
                ArrayList<Meal> arrayList = (ArrayList<Meal>) list.getMeals();
                setupBrowseRecyclerview(arrayList);
            }

            @Override
            public void onFailure(Call<CountryData> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupBrowseRecyclerview(ArrayList<Meal> arrayList) {
        if (arrayList != null && arrayList.size()>0){
            mRecyclerview.setVisibility(View.VISIBLE);
            no_meals.setVisibility(View.GONE);
            mRecyclerview.setAdapter(new CountryAndCategoryAdapter(this,arrayList));
        }
        else {
            mRecyclerview.setVisibility(View.GONE);
            no_meals.setVisibility(View.VISIBLE);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }

    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_msg);
        builder.setTitle(R.string.alert_title);
        builder.setPositiveButton(R.string.alert_pos_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(
                        Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        setContentView(R.layout.error_loading_screen);
        ImageView imageView = findViewById(R.id.errorimg);
        Glide.with(MainActivity.this)
                .load(R.drawable.anim).into(imageView);
        builder.show();
    }

    private void callFav() {
        getSupportActionBar().setTitle(R.string.favs);

        viewModel.getMlist().observe(this, new Observer<List<MealInfo>>() {
            @Override
            public void onChanged(@Nullable List<MealInfo> mealInfos) {
                if (mealInfos != null && mealInfos.size()>0){
                    mRecyclerview.setVisibility(View.VISIBLE);
                    no_meals.setVisibility(View.GONE);
                    mRecyclerview.setAdapter(new LatestAdapter(MainActivity.this, (ArrayList<MealInfo>) mealInfos));
                }else {
                    mRecyclerview.setVisibility(View.GONE);
                    no_meals.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void showProgress() {
        dialog.setTitle(getString(R.string.prog_title));
        dialog.setMessage(getString(R.string.prog_msd));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    private void callLatest() {
        getSupportActionBar().setTitle(R.string.latest);
        showProgress();
        Api api = Client.getClient().create(Api.class);
        Call<LatestMeal> call = api.getLatestMeal();
        call.enqueue(new Callback<LatestMeal>() {
            @Override
            public void onResponse(Call<LatestMeal> call, Response<LatestMeal> response) {
                LatestMeal mealInfo = response.body();
                ArrayList<MealInfo> list = (ArrayList<MealInfo>) mealInfo.getMeals();
                if (list != null && list.size()>0){
                    dialog.dismiss();
                    mRecyclerview.setVisibility(View.VISIBLE);
                    no_meals.setVisibility(View.GONE);
                    mRecyclerview.setAdapter(new LatestAdapter(MainActivity.this, list));

                }
                else {
                    dialog.dismiss();
                    mRecyclerview.setVisibility(View.GONE);
                    no_meals.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<LatestMeal> call, Throwable t) {
                dialog.dismiss();
                showAlert();
            }
        });


    }

    private void callRandom() {
        getSupportActionBar().setTitle(R.string.random);

        showProgress();
        Api api = Client.getClient().create(Api.class);
        Call<LatestMeal> call = api.getRandomMeal();
        call.enqueue(new Callback<LatestMeal>() {
            @Override
            public void onResponse(Call<LatestMeal> call, Response<LatestMeal> response) {
                LatestMeal mealInfo = response.body();
                ArrayList<MealInfo> list = (ArrayList<MealInfo>) mealInfo.getMeals();
                if (list != null && list.size()>0){
                    dialog.dismiss();
                    mRecyclerview.setVisibility(View.VISIBLE);
                    no_meals.setVisibility(View.GONE);
                    mRecyclerview.setAdapter(new LatestAdapter(MainActivity.this, list));

                }
                else {
                    dialog.dismiss();
                    mRecyclerview.setVisibility(View.GONE);
                    no_meals.setVisibility(View.VISIBLE);
                }}

            @Override
            public void onFailure(Call<LatestMeal> call, Throwable t) {
                dialog.dismiss();
                showAlert();
            }
        });
    }

}
