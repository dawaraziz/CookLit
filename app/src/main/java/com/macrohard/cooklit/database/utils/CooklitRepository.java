package com.macrohard.cooklit.database.utils;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.macrohard.cooklit.database.dao.CooklitDao;
import com.macrohard.cooklit.database.model.Ingredient;
import com.macrohard.cooklit.database.model.Recipe;

import java.util.List;

public class CooklitRepository {

    private CooklitDao mCooklitDao;
    private LiveData<List<Ingredient>> mAllIngredients;
    private LiveData<List<Recipe>> mAllRecipes;

    public CooklitRepository(Application application){
        CooklitDatabase db = CooklitDatabase.getDatabase(application);
        mCooklitDao = db.CooklitDao();
        mAllIngredients = mCooklitDao.getAllIngredients ();
        mAllRecipes = mCooklitDao.getAllRecipes();
    }

    public LiveData<List<Ingredient>> getAllIngredients(){
        return mAllIngredients;
    }

    public LiveData<List<Recipe>> getAllRecipes(){
        return mAllRecipes;
    }

    public void insert (Ingredient ingredient){
        new insertIngredient(mCooklitDao).execute(ingredient);
    }

    public void insert (Recipe recipe){
        new insertRecipe (mCooklitDao).execute(recipe);
    }

    private static class insertIngredient extends AsyncTask<Ingredient, Void, Void> {

        private CooklitDao mAsyncTaskDao;
        insertIngredient (CooklitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Ingredient... params) {
            mAsyncTaskDao.insertIngredient(params[0]);
            return null;
        }

    }

    private static class insertRecipe extends AsyncTask<Recipe, Void, Void> {

        private CooklitDao mAsyncTaskDao;
        insertRecipe (CooklitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            mAsyncTaskDao.insertRecipe(params[0]);
            return null;
        }

    }

}
