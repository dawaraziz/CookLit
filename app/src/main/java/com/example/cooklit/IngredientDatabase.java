package com.example.cooklit;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {com.example.cooklit.Ingredient.class}, version =1)
public abstract class IngredientDatabase extends RoomDatabase {
    public abstract IngredientDao ingredientDao();

    private static IngredientDatabase INSTANCE;

    static IngredientDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (IngredientDatabase.class){
                // if DB does not exist, create DB here
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IngredientDatabase.class, "ingredient_database")
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
        private final IngredientDao mDao;
        PopulateDbAsync(IngredientDatabase db) {
            mDao = db.ingredientDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            /* mDao.deleteAll();
            com.example.cooklit.Ingredient ingredient = new com.example.cooklit.Ingredient("Hello");
            mDao.insert(ingredient);
            ingredient = new com.example.cooklit.Ingredient("World");
            mDao.insert(ingredient);
            */

            return null;
        }

    }
}
