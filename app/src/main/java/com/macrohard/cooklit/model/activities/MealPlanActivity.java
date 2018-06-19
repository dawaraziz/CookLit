package com.macrohard.cooklit.model.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.ListView;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.support.adapters.RecipeListViewAdapter;
import com.macrohard.cooklit.support.adapters.TwoTextListViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class MealPlanActivity extends AppCompatActivity {

    CalendarView calendarView;
    ListView mealsForDayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        calendarView = findViewById(R.id.mealPlanCalendar);
        mealsForDayList = findViewById(R.id.mealsForDay);

        getScheduleInfo(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

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
