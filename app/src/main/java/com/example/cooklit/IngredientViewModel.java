package com.example.cooklit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {
    private IngredientRepository mRepository;
    private LiveData<List<Ingredient>> mAllIngredients;
    public IngredientViewModel (Application application){
        super(application);
        mRepository = new IngredientRepository(application);
        mAllIngredients = mRepository.getAllIngredients();
    }
    LiveData<List<com.example.cooklit.Ingredient>> getAllIngredients() {return mAllIngredients;}
    public void insert(com.example.cooklit.Ingredient ingredient) { mRepository.insert(ingredient);}
}
