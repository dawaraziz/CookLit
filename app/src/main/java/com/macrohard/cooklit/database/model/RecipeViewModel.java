package com.macrohard.cooklit.database.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.macrohard.cooklit.database.dao.CooklitDao;
import com.macrohard.cooklit.database.utils.CooklitDatabase;

import java.util.List;

// RecipeViewModel is reponsible for preapring and managing
//  the data for an Activity. It is used by activities
//  Activities should be able to observe changes in the viewmodel
public class RecipeViewModel extends AndroidViewModel {

    private CooklitDao cooklitDao;
    private LiveData<List<Recipe>> mAllRecipes;

    // AndroidViewModel accpets application as the only param
    public RecipeViewModel(Application application){
        super(application);
        cooklitDao = CooklitDatabase.getDatabase(application.getApplicationContext()).CooklitDao();
        //The line below is redundant, remove if possible
        mAllRecipes = cooklitDao.getAllRecipes();
    }


    // getAllRecipes is called by acitivites and it returns allRecipes
    public LiveData<List<Recipe>> getmAllRecipes() {
        return mAllRecipes;
    }

    // getRecipesbyDate returns List of Recipe that conatins the day
    public List<Recipe> getRecipesByDay(final String day) {
        return cooklitDao.getRecipesByDay(day);
    }

    // insert is called by activites and insert recipe into repository
    public void insert(Recipe recipe) {
        cooklitDao.insertRecipe(recipe);
    }

    public void deleteRecipe(Recipe recipe) {
        cooklitDao.deleteRecipe(recipe);}


}
