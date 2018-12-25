package com.example.venky.capstoneproject.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.venky.capstoneproject.Models.FavInfo;
import com.example.venky.capstoneproject.Models.Meal;
import com.example.venky.capstoneproject.Models.MealInfo;

import java.util.List;

/**
 * Created by ADMIN on 12/21/2018.
 */

public class MealsRepository {
    private MealDAO mMealDao;
    private LiveData<List<MealInfo>> mAllMeals;

    public MealsRepository(Application application) {
        MealRoomDatabase database = MealRoomDatabase.getDatabase(application);
        mMealDao = database.mealDAO();
        mAllMeals = mMealDao.getAllFavs();
    }
    public LiveData<List<MealInfo>> getmAllMeals() {
        return mAllMeals;
    }
    void insert(MealInfo word) {
        new insertAsyncTask(mMealDao).execute(word);
    }
    void delete(MealInfo word) {
        new deleteAsyncTask(mMealDao).execute(word);
    }

    boolean checkFav(String id) {
        return mMealDao.isCheckFav(id);
    }

    private static class insertAsyncTask extends AsyncTask<MealInfo, Void, Void> {

        private MealDAO mAsyncTaskDao;

        insertAsyncTask(MealDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MealInfo... params) {
            mAsyncTaskDao.addToFavs(params[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<MealInfo, Void, Void> {

        private MealDAO mAsyncTaskDao;

        deleteAsyncTask(MealDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MealInfo... params) {
            mAsyncTaskDao.delFromFav(params[0]);
            return null;
        }
    }
}
