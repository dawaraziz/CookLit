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


@Database(entities = {Ingredient.class}, version =2)
public abstract class IngredientDatabase extends RoomDatabase {
    public abstract CooklitDao CooklitDao();

    private static IngredientDatabase INSTANCE;

    static IngredientDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (IngredientDatabase.class){
                // if DB does not exist, create DB here
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IngredientDatabase.class, "ingredient_database")
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
        PopulateDbAsync(IngredientDatabase db) {
            mDao = db.CooklitDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll();
            Ingredient ingredient = new Ingredient("Hello", "3", "2018-5-7");
            mDao.insert(ingredient);
            ingredient = new Ingredient("World", "2", "2018-5-30");
            mDao.insert(ingredient);
            return null;
        }

    }
}
