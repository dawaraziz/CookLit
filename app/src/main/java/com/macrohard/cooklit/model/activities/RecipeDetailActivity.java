package com.macrohard.cooklit.model.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//TODO::Needs to be given a look at

public class RecipeDetailActivity extends AppCompatActivity {


    private TextView title;
    private TextView ingredientsText;

    private ScrollView scrollView;

    private ImageView titleImage;

    private Button confirmAddToMealPlanButton;
    private Button cancelAddToMealPlanButton;
    private Button goToRecipeButton;
    private Button addToMealPlanButton;

    private JSONObject mJSONObject;
    private String imageUri, titleText,ingredients,link;

    private Handler mHandler;

    private Dialog addToMealPlanDialog;

    private CheckBox mealPlanCheckbox;

    Map<Button,Boolean> weekDayButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        addToMealPlanDialog = createDialog();
        initializeWeekDayButtons();

        title = findViewById(R.id.recipeTitle);
        ingredientsText = findViewById(R.id.recipeDetailTextview);

        scrollView = findViewById(R.id.scrollView);

        titleImage = findViewById(R.id.recipeImage);

        goToRecipeButton = findViewById(R.id.gotoRecipe);
        addToMealPlanButton = findViewById(R.id.addToMealPlanButton);

        //TODO::Create an object call recipe
        mHandler = new Handler();
        Intent mIntent = getIntent();

        imageUri = mIntent.getStringExtra("imageuri");
        titleText = mIntent.getStringExtra("title");
        link = mIntent.getStringExtra("uri");
        ingredients = mIntent.getStringExtra("ingredients");

        ingredientsText.setText(ingredients);
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
                addToMealPlanDialog.show();
                resetWeekdayList();
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private Dialog createDialog(){
        final Dialog addToMealPlanDialog = new Dialog(this,R.style.default_Dialog);

        addToMealPlanDialog.setContentView(R.layout.activity_add_to_meal_plan);

        confirmAddToMealPlanButton = (Button) addToMealPlanDialog.findViewById(R.id.confirmAddToMealPlan_button);
        cancelAddToMealPlanButton = (Button) addToMealPlanDialog.findViewById(R.id.cancelAddToMealPlan_button);
        mealPlanCheckbox = addToMealPlanDialog.findViewById(R.id.openMealPlanCheckbox);

        confirmAddToMealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO::FM::Add logic to add recipe to meal plan
                addToMealPlanDialog.dismiss();
                Toast addToMealPlanConfirmationToast = Toast.makeText(getApplicationContext(), "Recipe added to your meal plan", Toast.LENGTH_SHORT);
                addToMealPlanConfirmationToast.show();
                if (mealPlanCheckbox.isChecked()) {
                    Intent mealPlanIntent = new Intent(RecipeDetailActivity.this, MealPlanActivity.class);
                    startActivity(mealPlanIntent);
                }
            }
        });

        cancelAddToMealPlanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addToMealPlanDialog.cancel();
            }
        });

        return addToMealPlanDialog;
    }

    private void initializeWeekDayButtons(){
        weekDayButtons = new HashMap<>();
        LinearLayout weekDaysContainer = addToMealPlanDialog.findViewById(R.id.weekdays_container);
        for(int i = 0 ; i < weekDaysContainer.getChildCount();i++){
            final Button weekDayButton = addToMealPlanDialog.findViewById(weekDaysContainer.getChildAt(i).getId());
            weekDayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!weekDayButtons.get(weekDayButton)) {
                        activateWeekDayButton(weekDayButton);
                    }else{
                        deActivateWeekDayButton(weekDayButton);
                    }
                    //TODO::FM::Add logic to save buttons in adding to meal plan, change the weekDays Button to hashmaps?
                }
            });
            weekDayButtons.put(weekDayButton,false);
        }
    }

    private void activateWeekDayButton(Button weekDayButton){
        final int whiteColorId = getApplication().getResources().getColor(R.color.white);
        final Drawable circularButtonSelectedDrawable = getResources().getDrawable(R.drawable.default_round_button_selected);
        weekDayButton.setTextColor(whiteColorId);
        weekDayButton.setBackground(circularButtonSelectedDrawable);
        weekDayButtons.put(weekDayButton,true);
    }

    private void deActivateWeekDayButton(Button weekDayButton){
        final Drawable circularButtonsDrawable = getResources().getDrawable(R.drawable.default_round_button);
        final int cyanColorId = getApplication().getResources().getColor(R.color.cookLitBlue);
        weekDayButton.setTextColor(cyanColorId);
        weekDayButton.setBackground(circularButtonsDrawable);
        weekDayButtons.put(weekDayButton,false);
    }

    private void resetWeekdayList(){
        for(Button button: weekDayButtons.keySet()){
            deActivateWeekDayButton(button);
        }
    }

}
