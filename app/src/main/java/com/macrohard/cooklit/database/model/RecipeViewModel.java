package com.macrohard.cooklit.database.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.macrohard.cooklit.database.utils.CooklitRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// RecipeViewModel is reponsible for preapring and managing
//  the data for an Activity. It is used by activities
//  Activities should be able to observe changes in the viewmodel
public class RecipeViewModel extends AndroidViewModel {

    private CooklitRepository mRepository;
    private LiveData<List<Recipe>> mAllRecipes;

    // AndroidViewModel accpets application as the only param
    public RecipeViewModel(Application application){
        super(application);
        mRepository = new CooklitRepository(application);
        mAllRecipes = mRepository.getAllRecipes();
    }


    // getAllRecipes is called by acitivites and it returns allRecipes
    public LiveData<List<Recipe>> getmAllRecipes() {
        return mAllRecipes;
    }


    // getRecipesbyDate returns List of Recipe that conatins the day
    public List<Recipe> getRecipesByDay(String day) {
        List<Recipe> recipesByDay = new ArrayList<Recipe>();
        List<Recipe> allRecipes = mAllRecipes.getValue();
        int recipeSize = allRecipes.size();
        for (int i = 0; i < recipeSize; i++) {
            List<String> dateList = new ArrayList<String>();
            try {
                JSONObject dateByJson = new JSONObject(allRecipes.get(i).getDate());
                JSONArray dateJsonArray = dateByJson.getJSONArray("date_array");
                Log.d("dateJsonArray", dateJsonArray.getString(0));
                for (int j = 0; j < dateJsonArray.length(); j++) {
                    dateList.add(dateJsonArray.getString(j));
                    Log.d("Date",dateList.get(0));
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            if (dateList.contains(day)) {
                recipesByDay.add(allRecipes.get(i));
                Log.d("recipesbyDay", recipesByDay.get(0).getDate());
            }
        }
        return recipesByDay;
    }

    // insert is called by activites and insert recipe into repository
    public void insert(Recipe recipe) {
        mRepository.insert(recipe);
    }

}
