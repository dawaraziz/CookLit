package com.macrohard.cooklit.database.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.macrohard.cooklit.database.utils.IngredientRepository;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {

    private IngredientRepository mRepository;
    private LiveData<List<Ingredient>> mAllIngredients;

    public IngredientViewModel (Application application){
        super(application);
        mRepository = new IngredientRepository(application);
        mAllIngredients = mRepository.getAllIngredients();
    }

    public LiveData<List<Ingredient>> getAllIngredients() {
        return mAllIngredients;
    }

    public void insert(Ingredient ingredient) {
        mRepository.insert(ingredient);
    }

}
