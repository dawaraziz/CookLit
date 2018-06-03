package com.macrohard.cooklit.database.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.macrohard.cooklit.database.utils.IngredientRepository;

import java.util.List;

// IngredientViewModel is reponsible for preapring and managing
//  the data for an Activity. It is used by activities
//  Activities should be able to observe changes in the viewmodel
public class IngredientViewModel extends AndroidViewModel {

    private IngredientRepository mRepository;
    private LiveData<List<Ingredient>> mAllIngredients;

    // AndroidViewModel accpets application as the only param
    public IngredientViewModel (Application application){
        super(application);
        mRepository = new IngredientRepository(application);
        mAllIngredients = mRepository.getAllIngredients();
    }

    // getAllIngreidents is called by acitivites and it returns allingredients
    public LiveData<List<Ingredient>> getAllIngredients() {
        return mAllIngredients;
    }

    // insert is called by activites and insert ingredient into repository
    public void insert(Ingredient ingredient) {
        mRepository.insert(ingredient);
    }

}
