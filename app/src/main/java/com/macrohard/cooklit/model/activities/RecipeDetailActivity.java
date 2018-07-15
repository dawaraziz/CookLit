package com.macrohard.cooklit.model.activities;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.database.model.Recipe;
import com.macrohard.cooklit.database.model.RecipeViewModel;
import com.macrohard.cooklit.model.dialogs.AddToMealPlanDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//TODO::Needs to be given a look at

public class RecipeDetailActivity extends AppCompatActivity {

    private Recipe currentRecipe;

    private TextView title;
    private TextView ingredientsText;

    private ScrollView scrollView;

    private ImageView titleImage;

    private AddToMealPlanDialog addToMealPlanDialog;

    private Button goToRecipeButton;
    private Button addToMealPlanButton;

    private JSONObject mJSONObject;
    private String imageUri, ingredients;

    private Handler mHandler;

    public Recipe getCurrentRecipe(){
        return currentRecipe;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        title = findViewById(R.id.recipeTitle);
        ingredientsText = findViewById(R.id.recipeDetailTextview);

        scrollView = findViewById(R.id.scrollView);

        titleImage = findViewById(R.id.recipeImage);

        goToRecipeButton = findViewById(R.id.gotoRecipe);
        addToMealPlanButton = findViewById(R.id.addToMealPlanButton);

        //TODO::Create an object call recipe
        mHandler = new Handler();
        Intent mIntent = getIntent();

        currentRecipe = new Recipe(mIntent.getStringExtra("title"),mIntent.getStringExtra("uri"));
        RecipeViewModel mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        addToMealPlanDialog = new AddToMealPlanDialog(this,R.style.default_Dialog, currentRecipe);

        imageUri = mIntent.getStringExtra("imageuri");
        ingredients = mIntent.getStringExtra("ingredients");

        ingredientsText.setText(ingredients);
        Picasso.with(RecipeDetailActivity.this).load(imageUri).into(titleImage);
        title.setText(currentRecipe.getName());

        goToRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(RecipeDetailActivity.this, RecipeActivity.class);
                i2.putExtra("uri",currentRecipe.getUri());
                startActivity(i2);
            }
        });

        addToMealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToMealPlanDialog.show();
                //resetWeekdayList();
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}
