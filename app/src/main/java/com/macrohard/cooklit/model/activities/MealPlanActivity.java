package com.macrohard.cooklit.model.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.ListView;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.database.model.Recipe;
import com.macrohard.cooklit.database.model.RecipeViewModel;
import com.macrohard.cooklit.support.adapters.TextAndItemListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealPlanActivity extends AppCompatActivity {

    private CalendarView mealPlanCalender;
    private ListView mealsForDayList;
    private RecipeViewModel mRecipeViewModel;
    private JSONArray dates = new JSONArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        mealPlanCalender = findViewById(R.id.mealPlanCalendar);
        mealsForDayList = findViewById(R.id.mealsForDay);

        // Get the ViewModel.
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        getScheduleInfo(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        // Create the observer which updates the UI
        final Observer<List<Recipe>> recipeObserver = new Observer<List<Recipe>>(){
            @Override
            public void onChanged(@Nullable final List<Recipe> recipes){
                // Update the UI here
                //recipeNameSample= recipes.get(0).getName();
                //recipeTimeSample= recipes.get(0).getTime();
                //Log.d("recipe_name",recipeNameSample);
                List<Recipe> recipeListMonday = mRecipeViewModel.getRecipesByDay("Monday");
//                    recipeNameSample = recipeListMonday.get(1).getName();
//                    recipeTimeSample = recipeListMonday.get(1).getTime();
                List<Recipe> recipeListFriday = mRecipeViewModel.getRecipesByDay("Friday");

                try {
                    JSONObject json = new JSONObject(recipes.get(0).getDate());
//                    dateJsonArray=  json.getJSONArray("date_array");
//                    if (dateJsonArray != null) {
//                        for (int i=0;i<dateJsonArray.length();i++){
//                            recipeDateSample.add(dateJsonArray.getString(i));
//                        }
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        //mRecipeViewModel.getmAllRecipes().observe(this,recipeObserver);

        mealPlanCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int dayOfWeek = getDayOfWeek(i,i1,i2);
                getScheduleInfo(dayOfWeek);
            }
        });

    }

    private void getScheduleInfo(int dayOfWeek){
        ArrayList<String> recipeNames = new ArrayList<>();
        ArrayList<String> timings = new ArrayList<>();
        List<Recipe> recipes = null;

        //TODO::FM::If the getRecipesByDay Function would work, meal plan would then be all good
        switch (dayOfWeek){
            case 1:
                recipes = mRecipeViewModel.getRecipesByDay("Su");
            case 2:
                recipes = mRecipeViewModel.getRecipesByDay("M");
            case 3:
                recipes = mRecipeViewModel.getRecipesByDay("T");
            case 4:
                recipes = mRecipeViewModel.getRecipesByDay("W");
            case 5:
                recipes = mRecipeViewModel.getRecipesByDay("Th");
            case 6:
                recipes = mRecipeViewModel.getRecipesByDay("F");
            case 7:
                recipes = mRecipeViewModel.getRecipesByDay("S");
        }

        if(recipes != null) {
            for (Recipe recipe : recipes) {
                recipeNames.add(recipe.getName());
                timings.add(recipe.getTime());
                if(!recipe.getRepeat()){
                    //TODO::FM::Remember to delete recipe here, Sean: I need the deleteRecipefunction here and the flags in the next line
                    //mRecipeViewModel.deleteRecipe(recipe);

                    //TODO::FM:: An if statement should be here checking if the recipe is saved. If it not, then delete, else just set the Onlysaved flag to true
                }
            }
        }

        TextAndItemListViewAdapter itemsAdapter =  new TextAndItemListViewAdapter(MealPlanActivity.this,
                R.layout.mealplan_schedule_view, recipeNames,timings);
        mealsForDayList.setAdapter(itemsAdapter);
    }


    private int getDayOfWeek(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

}
