package com.macrohard.cooklit.model.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.support.adapter.ListViewAdapter;

import java.util.ArrayList;

//import android.support.design.widget.Snackbar;

public class RecipeResultListActivity extends AppCompatActivity {



    public ListView RecipeView1,RecipeView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ArrayList<String> imageuris = new ArrayList<String>();
        imageuris.add("http://static.food2fork.com/StuffedChickenBreastSquare63bd.jpg");
        imageuris.add("http://static.food2fork.com/2080_MEDIUM1a18.jpg");

        ArrayList<String> urilinks = new ArrayList<>();
        urilinks.add("Stuffed Chicken Breast Recipe with Goat Cheese, Sun-Dried Tomatoes & Spinach");
        urilinks.add("Chicken, goat's cheese & cherry tomato bake");

        final ArrayList<String> linkToRecipes = new ArrayList<>();
        linkToRecipes.add("http://www.cookincanuck.com/2013/03/stuffed-chicken-breasts-Recipe-with-goat-cheese-sun-dried-tomatoes-spinach/");
        linkToRecipes.add("http://www.bbcgoodfood.com/recipes/2080/chicken-goats-cheese-and-cherry-tomato-bake");

        setContentView(R.layout.activity_recipe_result);
        RecipeView1 = (ListView)findViewById(R.id.listView1);
        ListViewAdapter recipeAdapter = new ListViewAdapter(RecipeResultListActivity.this,R.layout.elementview,imageuris,urilinks);
        RecipeView1.setAdapter(recipeAdapter);

        RecipeView1.setItemsCanFocus(false);
        RecipeView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isNetworkAvailable() == true){

                    Intent i2 = new Intent(RecipeResultListActivity.this, RecipeDetailActivity.class);
                    i2.putExtra("uri",linkToRecipes.get(i));
                    startActivity(i2);
                }
                else{
                    Snackbar.make(view, "Internet is not available, please retry", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
