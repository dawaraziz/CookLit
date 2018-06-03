package com.macrohard.cooklit.database.utils;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.macrohard.cooklit.database.dao.CooklitDao;
import com.macrohard.cooklit.database.model.Ingredient;

import java.util.List;

public class IngredientRepository {

    private CooklitDao mCooklitDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    public IngredientRepository(Application application){
        IngredientDatabase db = IngredientDatabase.getDatabase(application);
        mCooklitDao = db.CooklitDao();
        mAllIngredients = mCooklitDao.getAllIngredients ();
    }

    public LiveData<List<Ingredient>> getAllIngredients(){
        return mAllIngredients;
    }

    public void insert (Ingredient ingredient){
        new insertAsyncTask(mCooklitDao).execute(ingredient);
    }

    private static class insertAsyncTask extends AsyncTask<Ingredient, Void, Void> {

        private CooklitDao mAsyncTaskDao;
        insertAsyncTask(CooklitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Ingredient... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }

}
