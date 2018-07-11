package com.macrohard.cooklit.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.macrohard.cooklit.database.model.Ingredient;
import com.macrohard.cooklit.database.model.Recipe;

import java.util.List;

@Dao
public abstract class CooklitDao {

    // Dao for Ingredient
    @Insert
    public abstract void insertIngredient(Ingredient ingredient);

    @Query("DELETE FROM ingredient_table")
    public abstract void deleteAllIngredient();

    @Query("SELECT * FROM ingredient_table ORDER BY name ASC")
    public abstract LiveData<List<Ingredient>> getAllIngredients();

    @Query("SELECT * FROM ingredient_table WHERE name = :name")
    public abstract Ingredient fetchIngredientbyName(String name);

    @Update
    abstract void updateIngredient(Ingredient ingredient);

    @Delete
    public abstract void deleteIngredient(Ingredient ingredient);

    // DAO for Recipe
    @Insert
    public abstract void insertRecipe(Recipe recipe);

    @Query("DELETE FROM recipe_table")
    public abstract void deleteAllRecipes();

    @Query("SELECT * FROM recipe_table ORDER BY name ASC")
    public abstract LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipe_table WHERE name = :name")
    public abstract Recipe fetchRecipebyName(String name);

}
