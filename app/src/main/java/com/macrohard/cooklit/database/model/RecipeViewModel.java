package com.macrohard.cooklit.database.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.macrohard.cooklit.database.utils.CooklitRepository;

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

    // getAllIngreidents is called by acitivites and it returns allingredients
    public LiveData<List<Recipe>> getmAllRecipes() {
        return mAllRecipes;
    }

    // insert is called by activites and insert recipe into repository
    public void insert(Recipe recipe) {
        mRepository.insert(recipe);
    }

}
