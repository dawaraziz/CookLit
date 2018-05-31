package com.macrohard.cooklit.database.utils;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.macrohard.cooklit.database.dao.IngredientDao;
import com.macrohard.cooklit.database.model.Ingredient;

import java.util.List;

public class IngredientRepository {

    private IngredientDao mIngredientDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    public IngredientRepository(Application application){
        IngredientDatabase db = IngredientDatabase.getDatabase(application);
        mIngredientDao = db.ingredientDao();
        mAllIngredients = mIngredientDao.getAllIngredients ();
    }

    public LiveData<List<Ingredient>> getAllIngredients(){
        return mAllIngredients;
    }

    public void insert (Ingredient ingredient){
        new insertAsyncTask(mIngredientDao).execute(ingredient);
    }

    private static class insertAsyncTask extends AsyncTask<Ingredient, Void, Void> {

        private IngredientDao mAsyncTaskDao;
        insertAsyncTask(IngredientDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Ingredient... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }

}
