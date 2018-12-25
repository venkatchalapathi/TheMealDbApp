package com.example.venky.capstoneproject.Activities;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.venky.capstoneproject.Adapters.IngredsAdapter;
import com.example.venky.capstoneproject.Database.MealViewModel;
import com.example.venky.capstoneproject.MealAppWidget;
import com.example.venky.capstoneproject.Models.Ingreds;
import com.example.venky.capstoneproject.Utils.Api;
import com.example.venky.capstoneproject.Utils.Client;
import com.example.venky.capstoneproject.Utils.ExtractResources;
import com.example.venky.capstoneproject.Models.FavInfo;
import com.example.venky.capstoneproject.Models.LatestMeal;
import com.example.venky.capstoneproject.Models.MealInfo;
import com.example.venky.capstoneproject.Database.MealRoomDatabase;
import com.example.venky.capstoneproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailsActivity extends AppCompatActivity
{

    int mealId;
    ArrayList<MealInfo> list;
    ImageView imageView;
    TextView title,desc,videos_usrl;
    MealViewModel viewModel;
    //ProgressBar progressBar;
    RecyclerView ing;
    ImageView mButton;
    Toolbar toolbar;
    public static final String MEAL_ID = "meal_id";
    public static final String PREFERENCE_KEY = "widgets_key";
    public static final String MEAL_NAME_KEY = "meal_name_key";
    public static final String MEAL_IMAGE_KEY = "meal_image_key";
    CollapsingToolbarLayout collapsingToolbarLayout;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        collapsingToolbarLayout = findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this).get(MealViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView=findViewById(R.id.imageDetails);
        videos_usrl=findViewById(R.id.video_link);
        desc=findViewById(R.id.descDetails);

        mButton=findViewById(R.id.mat_button);
        ing=findViewById(R.id.ingredRecycler);

        Intent mIntent = getIntent();
        mealId =mIntent.getIntExtra(MEAL_ID,0);

        if (viewModel.checkFav(String.valueOf(mealId))){
            mButton.setImageResource(R.drawable.fav_fill);
        }
        list=new ArrayList<>();

        Api api= Client.getClient().create(Api.class);
        Call<LatestMeal> call=api.getMealDetails(mealId);
        call.enqueue(new Callback<LatestMeal>() {
            @Override
            public void onResponse(Call<LatestMeal> call, Response<LatestMeal> response) {
                LatestMeal mealInfo=response.body();
                list= (ArrayList<MealInfo>) mealInfo.getMeals();;
                setValues(list);
            }

            @Override
            public void onFailure(Call<LatestMeal> call, Throwable t) {
                Toast.makeText(MealDetailsActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void addToWidgets() {

        sharedPreferences = getSharedPreferences(PREFERENCE_KEY,0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(MEAL_NAME_KEY,list.get(0).getStrMeal());
        edit.putString(MEAL_IMAGE_KEY,list.get(0).getStrMealThumb());

        edit.apply();

        Intent intent = new Intent(this,MealAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(
                new ComponentName(getApplication(),MealAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }


    private void setValues(final ArrayList<MealInfo> list) {
        Picasso.with(MealDetailsActivity.this)
                .load(list.get(0).getStrMealThumb())
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        collapsingToolbarLayout.setTitle(list.get(0).getStrMeal());
       // title.setText(list.get(0).getStrMeal());
        desc.setText(list.get(0).getStrInstructions());
        videos_usrl.setText(list.get(0).getStrYoutube());
        getIngredsAndImages(list);

        videos_usrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(""+list.get(0).getStrYoutube());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        addToWidgets();
    }

    private void getIngredsAndImages(ArrayList<MealInfo> list) {
        ArrayList<Ingreds> ingList= ExtractResources.extractData(list);
        ing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ing.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        ing.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        ing.setAdapter(new IngredsAdapter(this,ingList));
    }

    public void favButton(View view) {

        if (viewModel.checkFav(String.valueOf(mealId))){
            mButton.setImageResource(R.drawable.fav_button);

            MealInfo entity= getAndSetValues();

            viewModel.delete(entity);
            Toast.makeText(this, R.string.remove_from_fav, Toast.LENGTH_SHORT).show();
        }else {

            mButton.setImageResource(R.drawable.fav_fill);
            MealInfo entity=getAndSetValues();

            viewModel.insert(entity);

            Toast.makeText(this, R.string.added_to_fav, Toast.LENGTH_SHORT).show();
        }
    }

    private MealInfo getAndSetValues() {
        MealInfo info=new MealInfo();
        info.setIdMeal(list.get(0).getIdMeal());
        info.setStrMeal(list.get(0).getStrMeal());
        info.setStrCategory(list.get(0).getStrCategory());
        info.setStrArea(list.get(0).getStrArea());
        info.setStrInstructions(list.get(0).getStrInstructions());
        info.setStrMealThumb(list.get(0).getStrMealThumb());
        info.setStrTags(list.get(0).getStrTags());
        info.setStrYoutube(list.get(0).getStrYoutube());

        return info;
    }
}