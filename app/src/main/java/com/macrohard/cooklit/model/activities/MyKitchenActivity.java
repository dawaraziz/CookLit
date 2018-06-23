package com.macrohard.cooklit.model.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.macrohard.cooklit.database.model.Ingredient;
import com.macrohard.cooklit.database.model.IngredientViewModel;
import com.macrohard.cooklit.R;
import com.macrohard.cooklit.controller.IngredientListAdapter;
import com.macrohard.cooklit.database.model.Recipe;
import com.macrohard.cooklit.database.model.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyKitchenActivity extends AppCompatActivity{
    private IngredientViewModel mIngredientViewModel;
    public static final int NEW_INGREDIENT_ACTIVITY_REQUEST_CODE = 1;
    private String[] ingredientList;
    private LiveData<List<Recipe>> mRecipes;
    private  String recipeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_kitchen);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final IngredientListAdapter adapter = new IngredientListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mIngredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
        mIngredientViewModel.getAllIngredients().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Ingredient> ingredients) {
                adapter.setIngredients(ingredients);
            }
        });




        // We might want to delete it at the end.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyKitchenActivity.this, AddItemActivity.class);
                startActivityForResult(intent, NEW_INGREDIENT_ACTIVITY_REQUEST_CODE);
            }
        });


        final Button openMealPlanButton = (Button) findViewById(R.id.openMealPlan);

        openMealPlanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mealPlanIntent = new Intent(MyKitchenActivity.this, MealPlanActivity.class);
                startActivity(mealPlanIntent);
            }
        });

        final Button bucketListButton = (Button) findViewById(R.id.bucketlist_button);

        bucketListButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent bucketListIntent = new Intent(getApplicationContext(), BucketListActivity.class);
                startActivityForResult(bucketListIntent, NEW_INGREDIENT_ACTIVITY_REQUEST_CODE);
            }

        });

        Button cooklitButton = (Button) findViewById(R.id.cooklit_button);

        cooklitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()){
                Intent i2;
                i2 = new Intent(MyKitchenActivity.this, RecipeResultListActivity.class);

                //Log.d("list", mRecipeViewModel.getmAllRecipes().getValue().get(0).getName());
                ingredientList = new String[mIngredientViewModel.getAllIngredients().getValue().size()];

                for(int i = 0; i < mIngredientViewModel.getAllIngredients().getValue().size();++i){
                    ingredientList[i] = mIngredientViewModel.getAllIngredients().getValue().get(i).getName();
                }

                i2.putExtra("ingredients",ingredientList);
                startActivity(i2);
            }
                else{

                    Snackbar.make(recyclerView, "Internet is not available, please retry", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

        });
        Button nutritionButton = findViewById(R.id.nutrition);
        nutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2;
                i2 = new Intent(MyKitchenActivity.this, NutritionActivity.class);
                startActivity(i2);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_INGREDIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int num = data.getExtras().size();
            System.out.print(num);
            if (num > 3) {
                for (int i = 1; i <= (num/3); i++) {
                    Ingredient ingredient = new Ingredient(data.getStringExtra("name" + String.valueOf(i)),
                            data.getStringExtra("quantity" + String.valueOf(i)),
                            data.getStringExtra("date" + String.valueOf(i)));
                    mIngredientViewModel.insert(ingredient);
                }
            } else {
                Ingredient ingredient = new Ingredient(data.getStringExtra(AddItemActivity.NAME),
                        data.getStringExtra(AddItemActivity.QUANTITY),
                        data.getStringExtra(AddItemActivity.DATE));
                mIngredientViewModel.insert(ingredient);
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_field,
                    Toast.LENGTH_LONG).show();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
