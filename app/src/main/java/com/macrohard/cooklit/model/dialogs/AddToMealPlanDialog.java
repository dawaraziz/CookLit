package com.macrohard.cooklit.model.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.database.model.Recipe;
import com.macrohard.cooklit.database.model.RecipeViewModel;
import com.macrohard.cooklit.model.activities.MealPlanActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddToMealPlanDialog extends Dialog  {

    private Recipe currentRecipe;

    private Activity activity;

    private Button confirmAddToMealPlanButton;
    private Button cancelAddToMealPlanButton;

    private CheckBox mealPlanCheckbox;

    private Map<Button,Boolean> weekDayButtons;

    private TimePicker timePicker;

    private RadioButton remindOnceRadioButton;
    private RadioButton remindEverytimeRadioButton;

    public AddToMealPlanDialog(Activity a, int theme_res_id, Recipe currentRecipe) {
        super(a , theme_res_id);
        this.activity = a;
        this.currentRecipe = currentRecipe;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_to_meal_plan);

        confirmAddToMealPlanButton = (Button) this.findViewById(R.id.confirmAddToMealPlan_button);
        cancelAddToMealPlanButton = (Button) this.findViewById(R.id.cancelAddToMealPlan_button);

        remindOnceRadioButton = this.findViewById(R.id.reminder_once);
        remindEverytimeRadioButton = this.findViewById(R.id.reminder_regular);

        mealPlanCheckbox = this.findViewById(R.id.openMealPlanCheckbox);

        timePicker = this.findViewById(R.id.mealPlanTimePicker);
        initializeWeekDayButtons();

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                remindOnceRadioButton.setChecked(true);
                resetWeekdayList();
            }
        });

        confirmAddToMealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                List<Recipe> savedRecipes = getResults();
                saveToDatabase(savedRecipes);
                Toast addToMealPlanConfirmationToast = Toast.makeText(getContext().getApplicationContext(), "Recipe added to your meal plan", Toast.LENGTH_SHORT);
                addToMealPlanConfirmationToast.show();
                dismiss();
                if (mealPlanCheckbox.isChecked()) {
                    Intent mealPlanIntent = new Intent(activity, MealPlanActivity.class);
                    activity.startActivity(mealPlanIntent);
                }
            }
        });

        cancelAddToMealPlanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cancel();
            }
        });

    }

    private void initializeWeekDayButtons(){
        weekDayButtons = new HashMap<>();
        LinearLayout weekDaysContainer = this.findViewById(R.id.weekdays_container);
        for(int i = 0 ; i < weekDaysContainer.getChildCount();i++){
            final Button weekDayButton = this.findViewById(weekDaysContainer.getChildAt(i).getId());
            weekDayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!weekDayButtons.get(weekDayButton)) {
                        activateWeekDayButton(weekDayButton);
                    }else{
                        deActivateWeekDayButton(weekDayButton);
                    }
                }
            });
            weekDayButtons.put(weekDayButton,false);
        }
    }

    private void activateWeekDayButton(Button weekDayButton){
        final int whiteColorId = getContext().getResources().getColor(R.color.white);
        final Drawable circularButtonSelectedDrawable = getContext().getResources().getDrawable(R.drawable.default_round_button_selected);
        weekDayButton.setTextColor(whiteColorId);
        weekDayButton.setBackground(circularButtonSelectedDrawable);
        weekDayButtons.put(weekDayButton,true);
    }

    private void deActivateWeekDayButton(Button weekDayButton){
        final Drawable circularButtonsDrawable = getContext().getResources().getDrawable(R.drawable.default_round_button);
        final int cyanColorId = getContext().getResources().getColor(R.color.cookLitBlue);
        weekDayButton.setTextColor(cyanColorId);
        weekDayButton.setBackground(circularButtonsDrawable);
        weekDayButtons.put(weekDayButton,false);
    }

    private void resetWeekdayList(){
        for(Button button: weekDayButtons.keySet()){
            deActivateWeekDayButton(button);
        }
    }

    private List<Recipe> getResults(){
        List<Recipe> savedDates = new ArrayList<>();
        String time = getFormattedTime();
        boolean remindRepeat= remindEverytimeRadioButton.isChecked();
        for(Button weekday: weekDayButtons.keySet()){
           if(weekDayButtons.get(weekday)){
                Recipe recipe = new Recipe(0, currentRecipe.getName(),currentRecipe.getUri());
                recipe.setDay((String)weekday.getText());
                recipe.setTime(time);
                recipe.setRepeat(remindRepeat);
                if(remindRepeat) {
                    recipe.setFormattedDate(new Date());
                }else{
                    //TODO::FM find the next instance of the date
                    recipe.setFormattedDate(new Date());
                }
                savedDates.add(recipe);
            }
        }

        return savedDates;
    }

    private String getFormattedTime(){
        String hour = String.valueOf(timePicker.getCurrentHour());
        String minute = String.valueOf(String.valueOf(timePicker.getCurrentMinute()));
        if(hour.length() == 1){
            hour = "0" + hour;
        }
        if(minute.length() == 1){
            minute = "0" + minute;
        }
        return hour + ":" + minute;
    }

    private void saveToDatabase(List<Recipe> recipesToBeSaved){
        RecipeViewModel recipeViewModel =  ViewModelProviders.of((FragmentActivity) this.activity).get(RecipeViewModel.class);
        for(Recipe recipe: recipesToBeSaved) {
            recipeViewModel.insert(recipe);
        }
    }

}

