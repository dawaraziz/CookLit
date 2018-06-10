package com.macrohard.cooklit.model.activities;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.macrohard.cooklit.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//TODO::Needs to be given a look at

public class RecipeDetailActivity extends AppCompatActivity {


    TextView title;
    TextView ingredientsText;

    ScrollView scrollView;

    ImageView titleImage;

    Button goToRecipeButton;
    Button addToMealPlanButton;

    JSONObject mJSONObject;
    String imageUri, titleText,ingredients,link;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        titleImage = findViewById(R.id.recipeImage);
        title = findViewById(R.id.recipeTitle);
        scrollView = findViewById(R.id.scrollView);
        ingredientsText = findViewById(R.id.recipeDetailTextview);
        goToRecipeButton = findViewById(R.id.gotoRecipe);
        addToMealPlanButton = findViewById(R.id.addToMealPlanButton);
        mHandler = new Handler();
        Intent mintent = getIntent();
        imageUri = mintent.getStringExtra("imageuri");
        titleText = mintent.getStringExtra("title");
        link = mintent.getStringExtra("uri");
        ingredients = mintent.getStringExtra("ingrediants");
        ingredientsText.setText(ingredients);
        //new Thread(mMessageSender).start();
        //ingredients = "";
        //ingredientsText.setText("Loading...");
        Picasso.with(RecipeDetailActivity.this).load(imageUri).into(titleImage);
        title.setText(titleText);

        goToRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(RecipeDetailActivity.this, RecipeActivity.class);
                i2.putExtra("uri",link);
                startActivity(i2);
            }
        });

        addToMealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog addToMealPlanDialog = new Dialog(RecipeDetailActivity.this,R.style.default_Dialog);
                addToMealPlanDialog.setContentView(R.layout.activity_add_to_meal_plan);
                addToMealPlanDialog.show();
                WindowManager.LayoutParams layoutParams = addToMealPlanDialog.getWindow().getAttributes();
                layoutParams.dimAmount = 0.3f;
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
