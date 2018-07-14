package com.macrohard.cooklit.model.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ListView;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.database.model.Ingredient;
import com.macrohard.cooklit.database.model.IngredientViewModel;
import com.macrohard.cooklit.database.model.Recipe;
import com.macrohard.cooklit.database.model.RecipeViewModel;
import com.macrohard.cooklit.support.adapters.RecipeListViewAdapter;
import com.macrohard.cooklit.support.adapters.TwoTextListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealPlanActivity extends AppCompatActivity {

    CalendarView calendarView;
    ListView mealsForDayList;
    private RecipeViewModel mRecipeViewModel;
    String recipeNameSample;
    String recipeTimeSample;
    ArrayList<String> recipeDateSample = new ArrayList<>();
    JSONArray dateJsonArray = new JSONArray();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        calendarView = findViewById(R.id.mealPlanCalendar);
        mealsForDayList = findViewById(R.id.mealsForDay);

        getScheduleInfo(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        // Get the ViewModel.
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);


        // Create the observer which updates the UI
        final Observer<List<Recipe>> recipeObserver = new Observer<List<Recipe>>(){
            @Override
            public void onChanged(@Nullable final List<Recipe> recipes){
                // Update the UI here

                //recipeNameSample= recipes.get(0).getName();
                //recipeTimeSample= recipes.get(0).getTime();
                //Log.d("recipe_name",recipeNameSample);

                try {
                    List<Recipe> recipeListMonday = mRecipeViewModel.getRecipesByDay("Monday");
                    recipeNameSample = recipeListMonday.get(1).getName();
                    recipeTimeSample = recipeListMonday.get(1).getTime();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    List<Recipe> recipeListFriday = mRecipeViewModel.getRecipesByDay("Friday");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject json = new JSONObject(recipes.get(0).getDate());
                    dateJsonArray=  json.getJSONArray("date_array");
                    if (dateJsonArray != null) {
                        for (int i=0;i<dateJsonArray.length();i++){
                            recipeDateSample.add(dateJsonArray.getString(i));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mRecipeViewModel.getmAllRecipes().observe(this,recipeObserver);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int dayOfWeek = getDayOfWeek(i,i1,i2);
                getScheduleInfo(dayOfWeek);
            }
        });

    }



    //TODO::FM::Need to remove variables, this part is a dummy line
    private void getScheduleInfo(int dayOfWeek){
        ArrayList<String> recipes = new ArrayList<>();
        ArrayList<String> timings = new ArrayList<>();

        // using Date example
        //ArrayList<String> timings = new ArrayList<>(recipeDateSample);
        //TODO::FM::Need to add business logic to get things from db
        if ((dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.FRIDAY) ){
            recipes.add(recipeNameSample);
            timings.add(recipeTimeSample);
        }
        TwoTextListViewAdapter itemsAdapter =  new TwoTextListViewAdapter(MealPlanActivity.this,
                R.layout.mealplan_schedule_view, recipes,timings);
        mealsForDayList.setAdapter(itemsAdapter);
    }


    private int getDayOfWeek(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

}
