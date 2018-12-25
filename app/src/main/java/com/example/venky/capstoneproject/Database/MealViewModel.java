package com.example.venky.capstoneproject.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.venky.capstoneproject.Models.FavInfo;
import com.example.venky.capstoneproject.Models.MealInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VENKY on 12/21/2018.
 */

public class MealViewModel extends AndroidViewModel {

    private MealsRepository mealsRepository;
    private LiveData<List<MealInfo>> mlist;
    public MealViewModel(@NonNull Application application) {
        super(application);
        mealsRepository = new MealsRepository(application);
        mlist = mealsRepository.getmAllMeals();
    }

    public LiveData<List<MealInfo>> getMlist() {
        return mlist;
    }
    public void insert(MealInfo mealInfo){
        mealsRepository.insert(mealInfo);
    }
    public void delete(MealInfo mealInfo){
        mealsRepository.delete(mealInfo);
    }

    public boolean checkFav(String id){
        return mealsRepository.checkFav(id);
    }
}
