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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealPlanActivity extends AppCompatActivity {

    CalendarView calendarView;
    ListView mealsForDayList;
    private RecipeViewModel mRecipeViewModel;
    private String recipeStr;

/*    mRecipeViewModel.getmAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
            }
        });
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        calendarView = findViewById(R.id.mealPlanCalendar);
        mealsForDayList = findViewById(R.id.mealsForDay);

        getScheduleInfo(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewModel.getmAllRecipes().observe(this, new Observer<List<Recipe>>() {

            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                recipeStr=mRecipeViewModel.getmAllRecipes().getValue().get(1).getName();
                Log.d("recipe_name",recipeStr);
            }
        });

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
        //TODO::FM::Need to add business logic to get things from db
        if ((dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.FRIDAY) ){
            recipes.add("Avocado Chicken Salad");
            timings.add("6pm");
        }
        TwoTextListViewAdapter itemsAdapter =  new TwoTextListViewAdapter(MealPlanActivity.this, R.layout.mealplan_schedule_view,recipes,timings);
        mealsForDayList.setAdapter(itemsAdapter);
    }


    private int getDayOfWeek(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

}
