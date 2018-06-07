package com.macrohard.cooklit.database.utils;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.macrohard.cooklit.database.dao.CooklitDao;
import com.macrohard.cooklit.database.model.Ingredient;
import com.macrohard.cooklit.database.model.Recipe;


@Database(entities = {Ingredient.class, Recipe.class}, version =1)
public abstract class CooklitDatabase extends RoomDatabase {
    public abstract CooklitDao CooklitDao();

    private static CooklitDatabase INSTANCE;

    static CooklitDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (CooklitDatabase.class){
                // if DB does not exist, create DB here
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CooklitDatabase.class, "cooklit_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final CooklitDao mDao;
        PopulateDbAsync(CooklitDatabase db) {
            mDao = db.CooklitDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAllIngredient();
            mDao.deleteAllRecipes();
            Ingredient ingredient = new Ingredient("Hello", "3", "2018-5-7");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("World", "2", "2018-5-30");
            mDao.insertIngredient(ingredient);
            Recipe recipe = new Recipe("spagettie", "www.spagettie.com");
            mDao.insertRecipe(recipe);
            return null;
        }

    }
}
