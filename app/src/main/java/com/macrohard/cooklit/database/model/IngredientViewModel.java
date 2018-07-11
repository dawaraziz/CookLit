package com.macrohard.cooklit.database.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.macrohard.cooklit.database.utils.CooklitRepository;

import java.util.ArrayList;
import java.util.List;

// IngredientViewModel is reponsible for preapring and managing
//  the data for an Activity. It is used by activities
//  Activities should be able to observe changes in the viewmodel
public class IngredientViewModel extends AndroidViewModel {

    private CooklitRepository mRepository;
    private LiveData<List<Ingredient>> mAllIngredients;

    // AndroidViewModel accpets application as the only param
    public IngredientViewModel (Application application){
        super(application);
        mRepository = new CooklitRepository(application);
        mAllIngredients = mRepository.getAllIngredients();
    }

    // getAllIngreidents is called by acitivites and it returns allingredients
    public LiveData<List<Ingredient>> getAllIngredients() {
        return mAllIngredients;
    }

    public List<Ingredient> getSelectedIngredients() {
        List<Ingredient> selectedIngredients = new ArrayList<Ingredient>();
        int ingredientSize = mAllIngredients.getValue().size();
        for (int i=0; i< ingredientSize; i++){
            //if selected ingredients, add to the list
            if (mAllIngredients.getValue().get(i).getSelected()){
                selectedIngredients.add(mAllIngredients.getValue().get(i));
            }
        }
        return selectedIngredients;
    }

    // insert is called by activites and insert ingredient into repository
    public void insert(Ingredient ingredient) {
        mRepository.insert(ingredient);
    }

    public void deleteIngreident(Ingredient ingredient) {mRepository.deleteIngredient(ingredient);}
    public void deleteAll() {mRepository.deleteAllIngredients();}
}
