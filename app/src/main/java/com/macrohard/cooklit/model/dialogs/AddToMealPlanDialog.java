package com.macrohard.cooklit.model.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.database.dao.CooklitDao;
import com.macrohard.cooklit.database.model.Recipe;
import com.macrohard.cooklit.database.model.RecipeViewModel;
import com.macrohard.cooklit.database.utils.CooklitDatabase;
import com.macrohard.cooklit.model.activities.MealPlanActivity;
import com.macrohard.cooklit.model.activities.RecipeDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        // TODO Auto-generated constructor stub
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
        String time = String.valueOf(timePicker.getCurrentHour()) + ":" + String.valueOf(timePicker.getCurrentMinute());
        boolean remindRepeat= remindEverytimeRadioButton.isChecked();
        ArrayList<String> scheduledDays = new ArrayList<>();
        for(Button weekday: weekDayButtons.keySet()){
            //TODO::Sean, this is where an item is added, no need to remove stuff from the loop, but if you want to, let me know first
            if(weekDayButtons.get(weekday)){
                scheduledDays.add((String)weekday.getText());
                Recipe recipe = new Recipe(0, currentRecipe.getName(),currentRecipe.getUri());
                JSONObject datesJson = new JSONObject();
                try {
                    datesJson.put("date_array", new JSONArray(scheduledDays));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast addToMealPlanFailedToast = Toast.makeText(getContext().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT);
                    addToMealPlanFailedToast.show();
                    this.cancel();
                }
                recipe.setDate(datesJson.toString());
                recipe.setTime(time);
                recipe.setRepeat(remindRepeat);
                savedDates.add(recipe);
                scheduledDays.clear();
            }
        }

        return savedDates;
    }

    private void saveToDatabase(List<Recipe> recipesToBeSaved){
        RecipeViewModel recipeViewModel =  ViewModelProviders.of((FragmentActivity) this.activity).get(RecipeViewModel.class);
        for(Recipe recipe: recipesToBeSaved) {
            recipeViewModel.insert(recipe);
        }
    }

}

