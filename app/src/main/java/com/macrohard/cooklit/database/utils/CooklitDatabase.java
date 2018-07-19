package com.macrohard.cooklit.database.utils;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.macrohard.cooklit.database.dao.CooklitDao;
import com.macrohard.cooklit.database.model.Ingredient;
import com.macrohard.cooklit.database.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@Database(entities = {Ingredient.class, Recipe.class}, version = 5)
public abstract class CooklitDatabase extends RoomDatabase {
    public abstract CooklitDao CooklitDao();

    private static CooklitDatabase INSTANCE;

    public static CooklitDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (CooklitDatabase.class){
                // if DB does not exist, create DB here
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CooklitDatabase.class, "cooklit_database")
                            .fallbackToDestructiveMigration()
                            //.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2){

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // didn't alter the table, so empty
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3){

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE recipe_table "
            + " ADD COLUMN saved BOOLEAN" );
        }
    };

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        /*
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
        */

        @Override
        public void onCreate (@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
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
            Ingredient ingredient = new Ingredient("Chicken", "2018-05-07");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("Avocado", "2018-05-30");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("Banana", "2018-06-30");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("Apple", "2018-07-30");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("Kiwi", "2018-07-31");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("Pineapple", "2018-08-02");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("Meet", "2018-08-03");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("Beef", "2018-10-02");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("Onion", "2018-01-01");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("Ginger", "2018-02-02");
            mDao.insertIngredient(ingredient);
            ingredient = new Ingredient("Tofu", "2018-03-03");

            mDao.insertIngredient(ingredient);

            return null;
        }

    }
}
