package com.example.venky.capstoneproject.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.venky.capstoneproject.Adapters.BrowseAdapter;
import com.example.venky.capstoneproject.Models.Category;
import com.example.venky.capstoneproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BrowseByActivity extends AppCompatActivity {

    private RecyclerView browse_recyclerview;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private static String SORT_ORDER = "sort_order";
    private static String BROWSE_KEY = "brows_by";
    private ArrayList<Category> value;
    private static final String CATEGORY = "categories";
    private static final String URL_STRING = "https://www.themealdb.com/api/json/v1/1/categories.php";
    public static final String INT_KEY = "key";
    private static final String ID_CATEGORY = "idCategory";
    private static final String STR_CATEGORY = "strCategory";
    private static final String STR_CATEGORY_THUMB = "strCategoryThumb";
    private static final String STR_CATEGORY_DESC = "strCategoryDescription";
    int[] country_images= {
            R.raw.arabic, R.raw.argentinian, R.raw.british,
            R.raw.canadian, R.raw.chines, R.raw.crotian,
            R.raw.dutch, R.raw.egyptian, R.raw.french,
            R.raw.greek, R.raw.indian, R.raw.irish,
            R.raw.italian, R.raw.jamaican, R.raw.japaneese,
            R.raw.malasiya, R.raw.mexican, R.raw.moraccan,
            R.raw.norwegiean, R.raw.portugeese, R.raw.russian,
            R.raw.slovakian, R.raw.spanish, R.raw.thai,
            R.raw.us, R.raw.viatnamies};
    String[] country_names;
    String[] names;
    int check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_by);
        browse_recyclerview = findViewById(R.id.brosebyrecycler);
        browse_recyclerview.setLayoutManager(new GridLayoutManager(this, 4));

        sharedPreferences = getSharedPreferences(SORT_ORDER, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();


        country_names = getResources().getStringArray(R.array.countries_string);
        names= getResources().getStringArray(R.array.names);
       // final int check = sharedPreferences.getInt(SORT_ORDER,3);
        check = getIntent().getIntExtra(INT_KEY,3);
        if (check == 3){
            getSupportActionBar().setTitle(R.string.country);
        }
        else {
            getSupportActionBar().setTitle(R.string.country);
        }
        new GetCategory().execute();
    }

    public void setValue(ArrayList<Category> value) {
        browse_recyclerview.setAdapter(
                new BrowseAdapter(BrowseByActivity.this, country_images, country_names, check, value));

    }

    class GetCategory extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {

            StringBuilder builder=new StringBuilder();
            try {
                Uri uri = Uri.parse(URL_STRING);
                URL url = new URL(uri.toString());
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                while (line!=null){
                    line=reader.readLine();
                    builder.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<Category> list = new ArrayList<>();
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray(CATEGORY);
                for (int i=0;i<array.length();i++){
                    JSONObject jsonObject = array.getJSONObject(i);
                    String id = jsonObject.optString(ID_CATEGORY);
                    String name = jsonObject.optString(STR_CATEGORY);
                    String img = jsonObject.optString(STR_CATEGORY_THUMB);
                    String desc = jsonObject.optString(STR_CATEGORY_DESC);
                    Category category = new Category(id,name,img,desc);
                    list.add(category);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            browse_recyclerview.setAdapter(new BrowseAdapter(BrowseByActivity.this,country_images,country_names,check,list));
        }
    }
}
