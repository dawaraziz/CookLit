package com.macrohard.cooklit.model.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.database.model.Recipe;
import com.macrohard.cooklit.database.model.RecipeViewModel;
import com.macrohard.cooklit.support.adapters.RecipeListViewAdapter;
import com.macrohard.cooklit.support.adapters.TextButtonItemListViewAdapter;

import java.util.List;

public class SavedRecipesActivity extends AppCompatActivity {

    private ListView savedRecipesList;
    private RecipeViewModel mRecipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);

        savedRecipesList = findViewById(R.id.savedRecipes);
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        //TODO::Need a function that returns saved recipes. Query should be like "SELECT * FROM recipes where saved = true". Goes in the line below.
//        List<Recipe> savedRecipes = mRecipeViewModel.getSavedRecipes();

//        TextButtonItemListViewAdapter recipeAdapter = new TextButtonItemListViewAdapter(SavedRecipesActivity.this,R.layout.elementview,imageuris,urilinks,tags);
//        RecipeView1.setAdapter(recipeAdapter);
//        RecipeView1.setItemsCanFocus(false);
//        RecipeView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if(isNetworkAvailable() == true){
//
//                    Intent i2 = new Intent(RecipeResultListActivity.this, RecipeDetailActivity.class);
//                    i2.putExtra("uri",linkToRecipes.get(i));
//                    i2.putExtra("imageuri",imageuris.get(i));
//                    i2.putExtra("title",urilinks.get(i));
//                    i2.putExtra("ingredients",ingredients.get(i));
//                    i2.putExtra("tags",tags.get(i));
//                    startActivity(i2);
//                }
//                else{
//                    Snackbar.make(view, "Internet is not available, please try again", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//
//                }
//            }
//        });
//    }

    }




}
