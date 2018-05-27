package com.example.cooklit;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {
    @Insert
    void insert(com.example.cooklit.Ingredient ingredient);

    @Query("DELETE FROM ingredient_table")
    void deleteAll();

    @Query("SELECT * FROM ingredient_table ORDER BY name ASC")
    LiveData<List<Ingredient>> getAllIngredients();

    @Query("SELECT * FROM ingredient_table WHERE name = :name")
    Ingredient fetchIngredientbyName(String name);

    @Update
    void updateIngredient(Ingredient ingredient);

    @Delete
    void deleteIngredient(Ingredient ingredient);


}
