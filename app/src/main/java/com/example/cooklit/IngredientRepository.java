package com.example.cooklit;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;

import java.util.List;

public class IngredientRepository {
    private IngredientDao mIngredientDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    IngredientRepository(Application application){
        IngredientDatabase db = IngredientDatabase.getDatabase(application);
        mIngredientDao = db.ingredientDao();
        mAllIngredients = mIngredientDao.getAllIngredients ();
    }

    LiveData<List<com.example.cooklit.Ingredient>> getAllIngredients(){
        return mAllIngredients;
    }

    public void insert (com.example.cooklit.Ingredient ingredient){
        new insertAsyncTask(mIngredientDao).execute(ingredient);
    }

    private static class insertAsyncTask extends AsyncTask<Ingredient, Void, Void> {
        private IngredientDao mAsyncTaskDao;
        insertAsyncTask(IngredientDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final com.example.cooklit.Ingredient... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }
}
